package com.app.ecom.store.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.app.ecom.store.dto.CustomPage;
import com.app.ecom.store.dto.ProductCategoryDto;
import com.app.ecom.store.model.ProductCategory;
import org.springframework.data.domain.Pageable;

public interface ProductCategoryService {
	
	Long getCategoryIdByName(String name);

	ProductCategoryDto getCategoryById(Long id);
	
	ProductCategoryDto getProductCategoryByName(String name);

	ProductCategoryDto addCategory(ProductCategoryDto productCategory);

	CustomPage<ProductCategoryDto> getCategories(Pageable pageable, Map<String, String> params);
	
	Set<ProductCategoryDto> getAllProductCategories();

	List<ProductCategory> getAllCategories();

	void deleteCategory(Long id);

	ProductCategory editCategory(ProductCategory productCategory);

	boolean deleteCategories(List<Long> ids);

	boolean deleteAllCategories();
}
