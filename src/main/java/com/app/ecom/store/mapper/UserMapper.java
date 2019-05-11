package com.app.ecom.store.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import com.app.ecom.store.dto.UserDto;
import com.app.ecom.store.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class UserMapper {
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    public User userDtoToUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        if(!StringUtils.isEmpty(userDto.getUsername())) {
        	user.setUsername(userDto.getUsername());
        }
        if(!StringUtils.isEmpty(userDto.getPassword())) {
        	user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
            user.setIsEnabled(false);
            user.setRoles(new HashSet<>());
        }
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
        return user;
    }
    
    public List<UserDto> usersToUserDtos(List<User> users) {
    	if(CollectionUtils.isEmpty(users)) {
    		return Collections.emptyList();
    	}
    	
    	List<UserDto> userDtos = new ArrayList<>();
    	users.stream().filter(Objects::nonNull).forEach(user -> userDtos.add(userToUserDto(user)));
    	return userDtos;
    }
    
    public UserDto userToUserDto(User user) {
    	UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setLanguage(user.getLanguage());
        userDto.setMobile(user.getMobile());
        userDto.setAddressLine1(user.getAddressLine1());
        userDto.setAddressLine2(user.getAddressLine2());
        userDto.setCity(user.getCity());
        userDto.setState(user.getState());
        userDto.setPincode(user.getPincode());
        userDto.setCountry(user.getCountry());
        return userDto;
    }
}
