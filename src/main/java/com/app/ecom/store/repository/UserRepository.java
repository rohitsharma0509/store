package com.app.ecom.store.repository;

import java.util.List;

import com.app.ecom.store.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);

	//List<User> findByMobileContainingOrFirstNameContainingOrLastNameContaining(String mobileOrName);
	List<User> findByMobileContaining(String mobileOrName);
}
