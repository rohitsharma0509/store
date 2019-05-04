package com.app.ecom.store.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.app.ecom.store.constants.RequestUrls;
import com.app.ecom.store.dto.CustomPage;
import com.app.ecom.store.dto.CustomerDto;
import com.app.ecom.store.dto.OrderDto;
import com.app.ecom.store.dto.ProductDto;
import com.app.ecom.store.dto.ShoppingCart;
import com.app.ecom.store.model.Order;
import com.app.ecom.store.service.OrderService;
import com.app.ecom.store.service.ProductService;
import com.app.ecom.store.service.ShoppingCartService;
import com.app.ecom.store.util.CommonUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OrderController {

	@Inject
	private OrderService orderService;
	
	@Inject
	private ProductService productService;

	@Inject
	private ShoppingCartService shoppingCartService;
	
	@Inject
	private CommonUtil commonUtil;
	
	@PostMapping(value = RequestUrls.BUY)
	public String buyNow(@ModelAttribute("customer") @Valid CustomerDto customerDto,
			HttpServletRequest request, @RequestParam(value = "productId", required=false) Long id) {
		OrderDto orderDto;
		if(null == id){
			ShoppingCart shoppingCart = shoppingCartService.getShoppingCart(request);
			shoppingCart.setCustomerDto(customerDto);
			orderDto = orderService.addOrder(shoppingCart.getProductDtos(), customerDto, shoppingCart.getTotalPrice());
			shoppingCartService.removeShoppingCart(request);
		} else {
			List<ProductDto> productDtos = new ArrayList<>();
			ProductDto productDto = productService.getProductByIdForCart(id);
			productDtos.add(productDto);
			orderDto = orderService.addOrder(productDtos, customerDto, productDto.getPerProductPrice());
		}
		return "redirect:orders/" + orderDto.getId();
	}
	
	@GetMapping(value = RequestUrls.GET_ORDERS)
	public String getOrder(Model model, @PathVariable(value = "id") Long id) {
		OrderDto orderDto = orderService.getOrder(id);
		model.addAttribute("orderDto", orderDto);
		return "order";
	}
	
	@GetMapping(value =RequestUrls.ORDERS)
	public String searchOrders(Model model, @ModelAttribute("order") Order order, @PageableDefault(page = 1, size = 10) Pageable pageable) {
		CustomPage<OrderDto> page = orderService.searchOrders(order, pageable);
		model.addAttribute("order", order);
		model.addAttribute("pagging", commonUtil.getPagging("orders", page.getPageNumber()+1, page.getTotalPages(), null));
		model.addAttribute("page", page);
		return "orders";	
	}
	
	@GetMapping(value = RequestUrls.DOWNLOAD_ORDER)
	public void downloadOrder(Model model, HttpServletResponse response,
			@PathVariable(value = "id") Long id) throws IOException {
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=\""+ id + ".pdf\"");
		ByteArrayOutputStream baos = orderService.createOrderPdf(id);
	    response.setContentLength(baos.size());
	    baos.writeTo(response.getOutputStream());
	}
}
