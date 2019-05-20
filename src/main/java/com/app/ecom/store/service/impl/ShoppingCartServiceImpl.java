package com.app.ecom.store.service.impl;

import javax.servlet.http.HttpSession;

import com.app.ecom.store.dto.ShoppingCart;
import com.app.ecom.store.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
	
	@Autowired
	private HttpSession session;

	@Override
	public ShoppingCart getShoppingCart() {
		ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
		
		if(shoppingCart == null){
			session.setAttribute("shoppingCart", new ShoppingCart());
		}
		return (ShoppingCart) session.getAttribute("shoppingCart");
	}

	@Override
	public ShoppingCart updateShoppingCart(ShoppingCart shoppingCart) {
		session.setAttribute("shoppingCart", shoppingCart);
		return shoppingCart;
	}
	
	public void removeShoppingCart(){
		session.removeAttribute("shoppingCart");
	}
}
