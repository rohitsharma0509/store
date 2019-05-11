package com.app.ecom.store.service;

import java.io.ByteArrayOutputStream;
import java.time.ZonedDateTime;

import com.app.ecom.store.dto.CustomPage;
import com.app.ecom.store.dto.OrderDto;
import com.app.ecom.store.dto.ProductDto;
import com.app.ecom.store.dto.UserDto;
import com.app.ecom.store.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
	OrderDto addOrder(java.util.List<ProductDto> productDtos, UserDto userDto, Double totalPrice);

	OrderDto getOrder(Long id);

	Page<Order> getOrders(Pageable pageable);
	
	ByteArrayOutputStream createOrderPdf(Long id);

	Long countByOrderDate(ZonedDateTime orderDate);
	
	Long countByOrderDateGreaterThanEqual(ZonedDateTime orderDate);

	CustomPage<OrderDto> searchOrders(String orderNumber, String fromDate, String toDate, Long userId, Pageable pageable);
}
