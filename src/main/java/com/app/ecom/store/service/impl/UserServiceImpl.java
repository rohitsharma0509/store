package com.app.ecom.store.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.app.ecom.store.dto.UserDto;
import com.app.ecom.store.events.RegistrationCompleteEvent;
import com.app.ecom.store.mapper.UserMapper;
import com.app.ecom.store.model.User;
import com.app.ecom.store.repository.UserRepository;
import com.app.ecom.store.service.UserService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.LocaleResolver;

@Service
public class UserServiceImpl implements UserService {
    @Inject
    private UserRepository userRepository;
    
    @Inject
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Inject
    private LocaleResolver localeResolver;
    
    @Inject
    private UserMapper userMapper;
    
    @Inject
    private ApplicationEventPublisher applicationEventPublisher;
    
    @Override
    public User createUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setLanguage(StringUtils.isEmpty(userDto.getLanguage()) ? "en" : userDto.getLanguage());
        user.setMobile(userDto.getMobile());
        user.setAddressLine1(userDto.getAddressLine1());
        user.setAddressLine2(userDto.getAddressLine2());
        user.setCity(userDto.getCity());
        user.setState(userDto.getState());
        user.setPincode(userDto.getPincode());
        user.setCountry(userDto.getCountry());
        user.setIsEnabled(false);
        user.setRoles(new HashSet<>());
        return userRepository.save(user);
    }
    
    @Override
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
	@Override
	public List<UserDto> getUserByMobileOrName(String mobileOrName) {
		return userMapper.usersToUserDtos(userRepository.findByMobileContaining(mobileOrName));
	}
    
    @Override
    public Page<User> getUsers(Pageable pageable) {
    	PageRequest request = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
    	return userRepository.findAll(request);
    }
    
    @Override
    public void updateLocale(HttpServletRequest request, HttpServletResponse response, String language){
    	localeResolver.setLocale(request, response, Locale.forLanguageTag(language));
    }

    @Override
    public void sendVerificationLink(User user, WebRequest request) {
        try {
            applicationEventPublisher.publishEvent(new RegistrationCompleteEvent(user, request.getLocale(), request.getContextPath()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
