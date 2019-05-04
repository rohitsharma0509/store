package com.app.ecom.store.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.app.ecom.store.model.Role;

@Component
public class RoleValidator  implements Validator {
	
	@Override
	public boolean supports(Class<?> aClass) {
		return Role.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty");
	}
}
