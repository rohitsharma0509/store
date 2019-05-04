package com.app.ecom.store.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.app.ecom.store.dto.CustomerDto;
import com.app.ecom.store.mapper.OrderMapper;
import com.app.ecom.store.model.Customer;
import com.app.ecom.store.repository.CustomerRepository;
import com.app.ecom.store.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService{
	
	@Inject
	private CustomerRepository customerRepository;
	
	@Inject
	private OrderMapper orderMapper;

	@Override
	public Page<Customer> getCustomers(Pageable pageable) {
		PageRequest request = PageRequest.of(pageable.getPageNumber() - 1,
				pageable.getPageSize(), pageable.getSort());
		return customerRepository.findAll(request);
	}

	@Override
	public Page<Customer> searchCustomers(Customer customer, Pageable pageable) {
		ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues().withIgnoreCase()
				.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("mobile", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("city", ExampleMatcher.GenericPropertyMatchers.contains());
		Example<Customer> example = Example.of(customer, matcher);
		PageRequest request = PageRequest.of(pageable.getPageNumber() - 1,
				pageable.getPageSize(), pageable.getSort());
		return customerRepository.findAll(example, request);
	}

	@Override
	public List<CustomerDto> searchCustomerByMobile(String mobile) {
		return orderMapper.customersToCustomerDtos(customerRepository.findByMobileContaining(mobile));
	}

}
