package com.app.ecom.store.service;

import java.io.ByteArrayOutputStream;
import java.time.ZonedDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.app.ecom.store.dto.CustomPage;
import com.app.ecom.store.dto.CustomerDto;
import com.app.ecom.store.dto.OrderDto;
import com.app.ecom.store.dto.ProductDto;
import com.app.ecom.store.model.Order;

public interface OrderService {
	OrderDto addOrder(java.util.List<ProductDto> productDtos, CustomerDto customerDto, Double totalPrice);

	OrderDto getOrder(Long id);

	Page<Order> getOrders(Pageable pageable);
	
	ByteArrayOutputStream createOrderPdf(Long id);

	Long countByOrderDate(ZonedDateTime orderDate);
	
	Long countByOrderDateGreaterThanEqual(ZonedDateTime orderDate);

	CustomPage<OrderDto> searchOrders(Order order, Pageable pageable);
}
