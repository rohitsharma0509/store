package com.app.ecom.store.controller;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import com.app.ecom.store.constants.FieldNames;
import com.app.ecom.store.constants.RequestUrls;
import com.app.ecom.store.dto.IdsDto;
import com.app.ecom.store.dto.ProductCategoryDto;
import com.app.ecom.store.dto.Response;
import com.app.ecom.store.model.ProductCategory;
import com.app.ecom.store.service.ProductCategoryService;
import com.app.ecom.store.util.CommonUtil;
import com.app.ecom.store.validator.ProductCategoryValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProductCategoryController {

	@Autowired
	private ProductCategoryService productCategoryService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private ProductCategoryValidator productCategoryValidator;
	
	@GetMapping(value = RequestUrls.ADD_CATEGORY)
	public String addCategory(Model model, @RequestParam(value = FieldNames.ID, required=false) Long id) {
		ProductCategoryDto productCategoryDto;
		if(id != null){
			productCategoryDto = productCategoryService.getCategoryById(id);
		}else {
			productCategoryDto = new ProductCategoryDto();
		}
		model.addAttribute(FieldNames.PRODUCT_CATEGORY_DTO, productCategoryDto);
		return "addCategory";
	}
	
	@PostMapping(value = RequestUrls.CATEGORIES)
	public String addCategory(Model model, @Valid ProductCategoryDto productCategoryDto, BindingResult bindingResult) {
		productCategoryValidator.validate(productCategoryDto, bindingResult);
		if (bindingResult.hasErrors()) {
			return "addCategory";
		}
		
		productCategoryService.addCategory(productCategoryDto);
		return "redirect:"+RequestUrls.CATEGORIES;
	}
	
	@GetMapping(value = RequestUrls.CATEGORIES)
	public String getCategories(Model model, @PageableDefault(page=1, size=10) Pageable pageable) {
		Page<ProductCategory> page = productCategoryService.getCategories(pageable);
		model.addAttribute(FieldNames.PAGGING, commonUtil.getPagging(RequestUrls.CATEGORIES, page.getNumber()+1, page.getTotalPages(), null));
	    model.addAttribute(FieldNames.PAGE, page);
		return "categories";
	}
	
	@GetMapping(value = RequestUrls.CATEGORIES_ALL)
	public String getAllCategories(Model model) {
		List<ProductCategory> categories = productCategoryService.getAllCategories();
		model.addAttribute(FieldNames.CATEGORIES, categories);
		return "categories";
	}
	
	@ResponseBody
	@PostMapping(value = RequestUrls.DELETE_CATEGORY)
	public Response deleteCategory(Model model, @PathVariable(FieldNames.ID) Long id) {
		Response response = productCategoryValidator.validateCategoryAssociation(Arrays.asList(id));

		if(HttpStatus.OK.value() == response.getCode()) {
			productCategoryService.deleteCategory(id);
		}
		return response;
	}
	
	@ResponseBody
	@PostMapping(value = RequestUrls.DELETE_BULK_CATEGORY)
	public Response deleteProducts(@RequestBody IdsDto idsDto) {
		Response response = productCategoryValidator.validateCategoryAssociation(idsDto.getIds());

		if(HttpStatus.OK.value() == response.getCode()) {
			productCategoryService.deleteCategories(idsDto.getIds());
		}
		return response;
	}
	
	@ResponseBody
	@PostMapping(value = RequestUrls.DELETE_ALL_CATEGORY)
	public Response deleteAllProducts() {
		Response response = productCategoryValidator.validateCategoryAssociation(null);

		if(HttpStatus.OK.value() == response.getCode()) {
			productCategoryService.deleteAllCategories();
		}
		return response;
	}

	@PutMapping(value = RequestUrls.CATEGORIES)
	public String editCategory(Model model, @ModelAttribute(FieldNames.PRODUCT_CATEGORY) ProductCategory productCategory) {
		productCategoryService.editCategory(productCategory);
		return "categories";
	}
}