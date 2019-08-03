package com.app.ecom.store.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import com.app.ecom.store.model.ProductCategory;
import com.app.ecom.store.repository.ProductCategoryRepository;
import com.app.ecom.store.service.ProductCategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
	
	@Inject
	private ProductCategoryRepository productCategoryRepository;
	
	@Override
	public Long getCategoryIdByName(String name) {
		return productCategoryRepository.findByName(name).getId();
	}

	@Override
	public ProductCategory getCategoryById(Long id) {
		return productCategoryRepository.findById(id).get();
	}

	@Override
	public ProductCategory addCategory(ProductCategory productCategory) {
		return productCategoryRepository.save(productCategory);
	}

	@Override
	public Page<ProductCategory> getCategories(Pageable pageable) {
		PageRequest request = PageRequest.of(pageable.getPageNumber() - 1,
				pageable.getPageSize(), pageable.getSort());
		return productCategoryRepository.findAll(request);
	}

	@Override
	public List<ProductCategory> getAllCategories() {
		return productCategoryRepository.findAll();
	}

	@Override
	public void deleteCategory(Long id) {
		productCategoryRepository.deleteById(id);
	}
	
	@Override
	@Transactional
	public boolean deleteAllCategories() {
		boolean isDeleted = false;
		try {
			productCategoryRepository.deleteAll();
			isDeleted = true;
		} catch(Exception e) {
			
		}
		return isDeleted;
	}

	@Override
	@Transactional
	public boolean deleteCategories(List<Long> ids) {
		boolean isDeleted = false;
		try {
			productCategoryRepository.deleteByIdIn(ids);
			isDeleted = true;
		} catch(Exception e) {
			
		}
		return isDeleted;
	}

	@Override
	public ProductCategory editCategory(ProductCategory productCategory) {
		return productCategoryRepository.save(productCategory);
	}
}