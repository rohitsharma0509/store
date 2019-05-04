package com.app.ecom.store.controller;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.validation.Valid;

import com.app.ecom.store.constants.FieldNames;
import com.app.ecom.store.constants.RequestUrls;
import com.app.ecom.store.dto.CustomPage;
import com.app.ecom.store.dto.ProductDto;
import com.app.ecom.store.model.Product;
import com.app.ecom.store.service.ProductCategoryService;
import com.app.ecom.store.service.ProductService;
import com.app.ecom.store.util.CommonUtil;
import com.app.ecom.store.validator.ProductValidator;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ProductController {
    
	@Inject
	private ProductService productService;
	
	@Inject
	private ProductCategoryService productCategoryService;
	
	@Inject
	private CommonUtil commonUtil;
	
	@Inject
	private ProductValidator productValidator;
	
	@GetMapping(value = RequestUrls.ADD_PRODUCT)
	public String addProduct(Model model, @RequestParam(value = FieldNames.ID, required=false) Long id) {
		ProductDto productDto;
		if(id != null){
			productDto = productService.getProductById(id);
		}else {
			productDto = new ProductDto();
		}
		model.addAttribute(FieldNames.PRODUCTDTO, productDto);
		model.addAttribute(FieldNames.CATEGORIES, productCategoryService.getAllCategories());
		return "addProduct";
	}
	
	@PostMapping(value = RequestUrls.PRODUCTS)
	public String addProduct(Model model, @ModelAttribute(FieldNames.PRODUCTDTO)  @Valid ProductDto productDto, BindingResult bindingResult) {
		productValidator.validate(productDto, bindingResult);
		if (bindingResult.hasErrors()) {
			model.addAttribute(FieldNames.CATEGORIES, productCategoryService.getAllCategories());
			return "addProduct";
		}
		
		productService.addProduct(productDto);
		return "redirect:"+RequestUrls.PRODUCTS;
	}
	
	@GetMapping(value =RequestUrls.PRODUCTS)
	public String getProducts(Model model, 
			@RequestParam(required = false) String categoryId,
			@RequestParam(required = false) String brandName,
			@RequestParam(required = false) String productName,
			@RequestParam(required = false) String statusId,
			@PageableDefault(page=1, size=10) Pageable pageable) {
		Map<String, String> params = new HashMap<>();
		params.put(FieldNames.CATEGORY_ID, categoryId);
		params.put(FieldNames.BRAND_NAME, brandName);
		params.put(FieldNames.PRODUCT_NAME, productName);
		params.put(FieldNames.STATUS_ID, statusId);
		CustomPage<Product> page = productService.searchProducts(pageable, params);
		java.util.Map<String, String> statuses = new java.util.HashMap<>();
		statuses.put("1", "Alert");
		statuses.put("2", "Available");
		statuses.put("3", "Out Of Stock");
		model.addAttribute("statuses", statuses);
		model.addAttribute(FieldNames.CATEGORIES, productCategoryService.getAllCategories());
		model.addAttribute(FieldNames.PAGGING, commonUtil.getPagging(RequestUrls.PRODUCTS, page.getPageNumber()+1, page.getTotalPages(), null));
	    model.addAttribute(FieldNames.PAGE, page);
		return "products";
	}
	
	@DeleteMapping(value = RequestUrls.DELETE_PRODUCT)
	public String deleteProduct(Model model, @PathVariable(FieldNames.ID) Long id) {
		productService.deleteProduct(id);
		return "products";
	}
	
	@PutMapping(value = RequestUrls.PRODUCTS)
	public String editProduct(Model model, @ModelAttribute(FieldNames.PRODUCT) Product product) {
		productService.editProduct(product);
		return "products";
	}
	
	@GetMapping(value = RequestUrls.PRODUCTS_IMPORT)
	public String importProducts() {
		return "importProducts";
	}
	
	@PostMapping(value = RequestUrls.PRODUCTS_SAVE)
	public String importProductsFromFile(@RequestParam(FieldNames.FILE) MultipartFile multiPartFile, @RequestParam(required= true) String fileType) {
		productService.importProducts(multiPartFile, fileType);
		return "importProducts";
	}
	
	//Non-admin API
	@GetMapping(value = RequestUrls.PRODUCT_ALL)
	public String getAllProducts(Model model,
			@RequestParam(required = false) String categoryId,
			@RequestParam(required = false) String brandName,
			@RequestParam(required = false) String productName,
			@RequestParam(required = false) String price,
			@PageableDefault(page=1, size=12) Pageable pageable) {
		Map<String, String> params = new HashMap<>();
		params.put(FieldNames.CATEGORY_ID, categoryId);
		params.put(FieldNames.BRAND_NAME, brandName);
		params.put(FieldNames.PRODUCT_NAME, productName);
		CustomPage<ProductDto> page = productService.searchProductDtos(pageable, params);
		model.addAttribute(FieldNames.CATEGORIES, productCategoryService.getAllCategories());
		model.addAttribute(FieldNames.PAGE, page);
		return "allProducts";
	}
	
	@GetMapping(value = RequestUrls.PRODUCTS_AJAX)
	public String loadProducts(Model model, @RequestParam(required = false) String categoryId,
			@RequestParam(required = false) String brandName,
			@RequestParam(required = false) String productName,
			@RequestParam(required = false) String price,
			@PageableDefault(page=1, size=12) Pageable pageable) {
		Map<String, String> params = new HashMap<>();
		params.put(FieldNames.CATEGORY_ID, categoryId);
		params.put(FieldNames.BRAND_NAME, brandName);
		params.put(FieldNames.PRODUCT_NAME, productName);
		CustomPage<ProductDto> page = productService.searchProductDtos(pageable, params);
		model.addAttribute(FieldNames.PAGE, page);
		return "productListing";
	}
}