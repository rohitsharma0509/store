package com.app.ecom.store.repository;

import java.util.List;

import com.app.ecom.store.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	void deleteByIdIn(List<Long> ids);
}
