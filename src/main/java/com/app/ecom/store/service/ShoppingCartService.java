package com.app.ecom.store.service;

import javax.servlet.http.HttpServletRequest;

import com.app.ecom.store.dto.ShoppingCart;

public interface ShoppingCartService {

	ShoppingCart getShoppingCart(HttpServletRequest request);
	
	ShoppingCart updateShoppingCart(HttpServletRequest request, ShoppingCart shoppingCart);
	
	void removeShoppingCart(HttpServletRequest request);
}
