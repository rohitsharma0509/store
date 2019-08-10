package com.app.ecom.store.validator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.app.ecom.store.dto.ProductCategoryDto;
import com.app.ecom.store.dto.Response;
import com.app.ecom.store.enums.ErrorCode;
import com.app.ecom.store.service.ProductCategoryService;
import com.app.ecom.store.service.ProductService;
import com.app.ecom.store.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ProductCategoryValidator implements Validator {
	
	@Autowired
	private ProductCategoryService productCategoryService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Override
	public boolean supports(Class<?> aClass) {
		return ProductCategoryDto.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		ProductCategoryDto productCategoryDto = (ProductCategoryDto) o;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", commonUtil.getMessage(ErrorCode.ERR000003.getCode()));
		
		if (productCategoryService.getProductCategoryByName(productCategoryDto.getName()) != null) {
			errors.rejectValue("name", commonUtil.getMessage(ErrorCode.ERR000016.getCode()));
        }
	}
	
	public Response validateCategoryAssociation(List<Long> categoryIds) {
		Set<ProductCategoryDto> productCategoryDtos = new HashSet<>();
		if(CollectionUtils.isEmpty(categoryIds)) {
			productCategoryDtos = productCategoryService.getAllProductCategories();
			categoryIds = productCategoryDtos.stream().map(ProductCategoryDto::getId).collect(Collectors.toList());
		}
		
		String errorCode = productCategoryDtos.size() <= 1 ? ErrorCode.ERR000017.getCode() : ErrorCode.ERR000018.getCode();
		Long productCount = productService.countByCategoryIdIn(categoryIds);
		return commonUtil.getResponse(productCount > 0, errorCode);
	}
}
