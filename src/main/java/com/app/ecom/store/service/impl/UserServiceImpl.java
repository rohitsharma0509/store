package com.app.ecom.store.service.impl;

import java.util.Locale;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.app.ecom.store.dto.UserDto;
import com.app.ecom.store.events.RegistrationCompleteEvent;
import com.app.ecom.store.mapper.UserMapper;
import com.app.ecom.store.model.User;
import com.app.ecom.store.repository.UserRepository;
import com.app.ecom.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.LocaleResolver;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
    private UserRepository userRepository;
    
    @Autowired
    private LocaleResolver localeResolver;
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    
    @Override
    public User createUser(UserDto userDto) {
        return userRepository.save(userMapper.userDtoToUser(userDto));
    }
    
    @Override
    public void updateUser(UserDto userDto) {
    	Optional<User> optionalUser = userRepository.findById(userDto.getId());
    	if(optionalUser.isPresent()) {
    		User userToUpdate = optionalUser.get();
    		userToUpdate.setFirstName(userDto.getFirstName());
    		userToUpdate.setLastName(userDto.getLastName());
    		userToUpdate.setEmail(userDto.getEmail());
    		userToUpdate.setMobile(userDto.getMobile());
    		userToUpdate.setLanguage(userDto.getLanguage());
    		userRepository.save(userToUpdate);
    	}
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    @Override
    public UserDto findUserByUsername(String username) {
        return userMapper.userToUserDto(userRepository.findByUsername(username));
    }
    
	@Override
	public Set<UserDto> getUserByMobileOrName(String mobileOrName) {
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

	@Override
	public UserDto findUserById(Long id) {
		Optional<User> optionalUser = userRepository.findById(id);
		if(optionalUser.isPresent()) {
			return userMapper.userToUserDto(optionalUser.get());
		} else {
			return null;
		}
	}
}
