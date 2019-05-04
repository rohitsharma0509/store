package com.app.ecom.store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.ecom.store.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	List<Customer> findByMobileContaining(String mobile);
}
