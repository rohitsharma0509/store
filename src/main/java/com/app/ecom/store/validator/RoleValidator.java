package com.app.ecom.store.validator;

import com.app.ecom.store.dto.RoleDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class RoleValidator  implements Validator {
	
	@Override
	public boolean supports(Class<?> aClass) {
		return RoleDto.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty");
	}
}
