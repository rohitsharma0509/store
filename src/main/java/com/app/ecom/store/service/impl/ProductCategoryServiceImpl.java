package com.app.ecom.store.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import com.app.ecom.store.dto.ProductCategoryDto;
import com.app.ecom.store.mapper.ProductCategoryMapper;
import com.app.ecom.store.model.ProductCategory;
import com.app.ecom.store.repository.ProductCategoryRepository;
import com.app.ecom.store.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
	
	@Autowired
	private ProductCategoryRepository productCategoryRepository;
	
	@Autowired
	private ProductCategoryMapper productCategoryMapper;
	
	@Override
	public Long getCategoryIdByName(String name) {
		ProductCategory productCategory = productCategoryRepository.findByName(name);
		return null == productCategory ? null : productCategory.getId();
	}

	@Override
	public ProductCategoryDto getCategoryById(Long id) {
		Optional<ProductCategory> optionalProductCategory = productCategoryRepository.findById(id);
		if(optionalProductCategory.isPresent()) {
			return productCategoryMapper.productCategoryToProductCategoryDto(optionalProductCategory.get());
		} else {
			return null;
		}
	}
	

	@Override
	public ProductCategoryDto getProductCategoryByName(String name) {
		return productCategoryMapper.productCategoryToProductCategoryDto(productCategoryRepository.findByName(name));
	}

	@Override
	public ProductCategoryDto addCategory(ProductCategoryDto productCategoryDto) {
		return productCategoryMapper.productCategoryToProductCategoryDto(productCategoryRepository.save(productCategoryMapper.productCategoryDtoToProductCategory(productCategoryDto)));
	}

	@Override
	public Page<ProductCategory> getCategories(Pageable pageable) {
		PageRequest request = PageRequest.of(pageable.getPageNumber() - 1,
				pageable.getPageSize(), pageable.getSort());
		return productCategoryRepository.findAll(request);
	}
	
	@Override
	public Set<ProductCategoryDto> getAllProductCategories() {
		return productCategoryMapper.productCategoriesToProductCategoryDtos(new HashSet<>(productCategoryRepository.findAll()));
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