package com.app.ecom.store.service;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.app.ecom.store.dto.CustomPage;
import com.app.ecom.store.dto.UserDto;
import com.app.ecom.store.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.web.context.request.WebRequest;

public interface UserService {
    User createUser(UserDto userDto);
    
    void updateUser(UserDto userDto);

    User findByUsername(String username);
    
    UserDto findUserByUsername(String username);
    
    CustomPage<UserDto> getUsers(Pageable pageable, Map<String, String> params);
    
    Set<UserDto> getUserByMobileOrName(String mobileOrName);

    void updateLocale(HttpServletRequest request, HttpServletResponse response, String language);

    void sendVerificationLink(User user, WebRequest request);

	UserDto findUserById(Long id);
}
