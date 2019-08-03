package com.app.ecom.store.validator;

import com.app.ecom.store.model.ProductCategory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ProductCategoryValidator implements Validator {
	@Override
	public boolean supports(Class<?> aClass) {
		return ProductCategory.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty");
	}	
}
