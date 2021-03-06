package com.app.ecom.store.service.impl;

import java.util.List;

import com.app.ecom.store.model.Supplier;
import com.app.ecom.store.repository.SupplierRepository;
import com.app.ecom.store.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SupplierServiceImpl implements SupplierService{

	@Autowired
	private SupplierRepository supplierRepository;

	@Override
	public List<Supplier> getAllSuppliers() {
		return supplierRepository.findAll();
	}

	@Override
	public Supplier addSupplier(Supplier supplier) {
		return supplierRepository.save(supplier);
	}

	@Override
	public void deleteSupplier(Long id) {
		supplierRepository.deleteById(id);
	}

	@Override
	public Page<Supplier> getSuppliers(Pageable pageable) {
		PageRequest request = PageRequest.of(pageable.getPageNumber() - 1,
				pageable.getPageSize(), pageable.getSort());
		return supplierRepository.findAll(request);
	}

	@Override
	public Supplier editSupplier(Supplier supplier) {
		return supplierRepository.save(supplier);
	}

	@Override
	public Supplier getSupplierById(Long id) {
		return supplierRepository.findById(id).get();
	}
}