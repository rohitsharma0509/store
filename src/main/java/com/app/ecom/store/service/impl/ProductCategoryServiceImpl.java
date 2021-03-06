package com.app.ecom.store.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import com.app.ecom.store.constants.Constants;
import com.app.ecom.store.dto.CustomPage;
import com.app.ecom.store.dto.ProductCategoryDto;
import com.app.ecom.store.dto.SearchCriteria;
import com.app.ecom.store.mapper.ProductCategoryMapper;
import com.app.ecom.store.model.ProductCategory;
import com.app.ecom.store.querybuilder.QueryBuilder;
import com.app.ecom.store.repository.ProductCategoryRepository;
import com.app.ecom.store.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
	
	@Autowired
	private ProductCategoryRepository productCategoryRepository;
	
	@Autowired
	private ProductCategoryMapper productCategoryMapper;
	
	@Autowired
	private QueryBuilder queryBuilder;
	
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
	public CustomPage<ProductCategoryDto> getCategories(Pageable pageable, Map<String, String> params) {	
		List<SearchCriteria> criterias = new ArrayList<>();
		int offset = (pageable.getPageNumber() - 1)*pageable.getPageSize();
		int limit = offset + pageable.getPageSize();
		
		StringBuilder query = new StringBuilder("select * from product_category where 1=1 ");
		StringBuilder countQuery = new StringBuilder("select count(category_id) count from product_category where 1=1 ");
		
		if(!StringUtils.isEmpty(params.get("name"))){
			query.append(" and category_name like :name");
			countQuery.append(" and category_name like :name");
			criterias.add(new SearchCriteria("name", params.get("name"), Constants.LIKE));
		}
		
		query.append(" limit "+offset+", "+limit);
		System.out.println("Query: "+query);
		List<ProductCategory> productCategories = queryBuilder.getByQuery(query.toString(), criterias, ProductCategory.class);
		Integer totalRecords = queryBuilder.countByQuery(countQuery.toString(), criterias);
		System.out.println(totalRecords);
		CustomPage<ProductCategoryDto> page = new CustomPage<>();
		Set<ProductCategoryDto> productCategoryDtos = productCategoryMapper.productCategoriesToProductCategoryDtos(totalRecords > 0 ? new HashSet<>(productCategories) : null);
		page.setContent(CollectionUtils.isEmpty(productCategoryDtos) ? null : new ArrayList<>(productCategoryDtos));
		page.setPageNumber(pageable.getPageNumber() - 1);
		page.setSize(pageable.getPageSize());
		page.setTotalPages((int)Math.ceil((double)totalRecords/pageable.getPageSize()));
		return page;
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