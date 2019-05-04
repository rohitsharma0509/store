package com.app.ecom.store.controller;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import com.app.ecom.store.constants.FieldNames;
import com.app.ecom.store.constants.RequestUrls;
import com.app.ecom.store.model.ProductCategory;
import com.app.ecom.store.service.ProductCategoryService;
import com.app.ecom.store.util.CommonUtil;
import com.app.ecom.store.validator.ProductCategoryValidator;
import org.springframework.data.domain.Page;
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

@Controller
public class ProductCategoryController {

	@Inject
	private ProductCategoryService productCategoryService;
	
	@Inject
	private CommonUtil commonUtil;
	
	@Inject
	private ProductCategoryValidator productCategoryValidator;
	
	@GetMapping(value = RequestUrls.ADD_CATEGORY)
	public String addCategory(Model model, @RequestParam(value = FieldNames.ID, required=false) Long id) {
		ProductCategory productCategory;
		if(id != null){
			productCategory = productCategoryService.getCategoryById(id);
		}else {
			productCategory = new ProductCategory();
		}
		model.addAttribute(FieldNames.PRODUCT_CATEGORY, productCategory);
		return "addCategory";
	}
	
	@PostMapping(value = RequestUrls.CATEGORIES)
	public String addCategory(Model model, @Valid ProductCategory productCategory, BindingResult bindingResult) {
		productCategoryValidator.validate(productCategory, bindingResult);
		if (bindingResult.hasErrors()) {
			return "addCategory";
		}
		
		productCategoryService.addCategory(productCategory);
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
	
	@DeleteMapping(value = RequestUrls.DELETE_CATEGORY)
	public String deleteCategory(Model model, @PathVariable(FieldNames.ID) Long id) {
		productCategoryService.deleteCategory(id);
		return "categories";
	}
	
	@PutMapping(value = RequestUrls.CATEGORIES)
	public String editCategory(Model model, @ModelAttribute(FieldNames.PRODUCT_CATEGORY) ProductCategory productCategory) {
		productCategoryService.editCategory(productCategory);
		return "categories";
	}
}