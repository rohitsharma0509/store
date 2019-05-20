package com.app.ecom.store.dto;

import java.util.HashSet;
import java.util.Set;

public class OrderDto {
	private Long id;

	private String orderNumber;

	private Double totalAmount;

	private String orderDate;
	
	private String status;

	private UserDto userDto;

	private Set<ProductDto> productDtos = new HashSet<>();
	
	private AddressDto addressDto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public UserDto getUserDto() {
		return userDto;
	}

	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}

	public Set<ProductDto> getProductDtos() {
		return productDtos;
	}

	public void setProductDtos(Set<ProductDto> productDtos) {
		this.productDtos = productDtos;
	}

	public AddressDto getAddressDto() {
		return addressDto;
	}

	public void setAddressDto(AddressDto addressDto) {
		this.addressDto = addressDto;
	}

}
