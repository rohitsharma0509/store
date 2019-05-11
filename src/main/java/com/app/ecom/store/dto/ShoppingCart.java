package com.app.ecom.store.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private UserDto userDto;

	private List<ProductDto> productDtos = new ArrayList<>();
	
	private Double totalPrice = 0.0;

	public UserDto getUserDto() {
		return userDto;
	}

	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}

	public List<ProductDto> getProductDtos() {
		return productDtos;
	}

	public void setProductDtos(List<ProductDto> productDtos) {
		this.productDtos = productDtos;
	}
	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
}
