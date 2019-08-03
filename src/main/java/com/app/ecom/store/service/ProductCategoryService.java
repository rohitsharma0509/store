package com.app.ecom.store.service;

import java.util.List;

import com.app.ecom.store.model.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductCategoryService {
	
	Long getCategoryIdByName(String name);

	ProductCategory getCategoryById(Long id);

	ProductCategory addCategory(ProductCategory productCategory);

	Page<ProductCategory> getCategories(Pageable pageable);

	List<ProductCategory> getAllCategories();

	void deleteCategory(Long id);

	ProductCategory editCategory(ProductCategory productCategory);

	boolean deleteCategories(List<Long> ids);

	boolean deleteAllCategories();
}
