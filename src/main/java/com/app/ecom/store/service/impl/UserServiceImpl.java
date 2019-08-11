package com.app.ecom.store.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.app.ecom.store.constants.Constants;
import com.app.ecom.store.dto.CustomPage;
import com.app.ecom.store.dto.SearchCriteria;
import com.app.ecom.store.dto.UserDto;
import com.app.ecom.store.events.RegistrationCompleteEvent;
import com.app.ecom.store.mapper.UserMapper;
import com.app.ecom.store.model.User;
import com.app.ecom.store.querybuilder.QueryBuilder;
import com.app.ecom.store.repository.UserRepository;
import com.app.ecom.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
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
	private QueryBuilder queryBuilder;
    
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
	public CustomPage<UserDto> getUsers(Pageable pageable, Map<String, String> params) {
		List<SearchCriteria> criterias = new ArrayList<>();
		int offset = (pageable.getPageNumber() - 1)*pageable.getPageSize();
		int limit = offset + pageable.getPageSize();
		
		StringBuilder query = new StringBuilder("select * from users where 1=1 ");
		StringBuilder countQuery = new StringBuilder("select count(user_id) count from users where 1=1 ");
		
		if(!StringUtils.isEmpty(params.get("name"))){
			query.append(" and (first_name like :name or last_name like :name)");
			countQuery.append(" and (first_name like :name or last_name like :name)");
			criterias.add(new SearchCriteria("name", params.get("name"), Constants.LIKE));
		}
		if(!StringUtils.isEmpty(params.get("email"))){
			query.append(" and email like :email");
			countQuery.append(" and email like :email");
			criterias.add(new SearchCriteria("email", params.get("email"), Constants.LIKE));
		}
		
		if(!StringUtils.isEmpty(params.get("mobile"))){
			query.append(" and mobile like :mobile");
			countQuery.append(" and mobile like :mobile");
			criterias.add(new SearchCriteria("mobile", params.get("mobile"), Constants.LIKE));
		}
		
		if(pageable.getSort() != null) {
			
		}
		
		query.append(" limit "+offset+", "+limit);
		System.out.println("Query: "+query);
		List<User> users = queryBuilder.getByQuery(query.toString(), criterias, User.class);
		Integer totalRecords = queryBuilder.countByQuery(countQuery.toString(), criterias);
		System.out.println(totalRecords);
		CustomPage<UserDto> page = new CustomPage<>();
		Set<UserDto> userDtos = userMapper.usersToUserDtos(totalRecords > 0 ? new HashSet<>(users) : null);
		page.setContent(CollectionUtils.isEmpty(userDtos) ? null : new ArrayList<>(userDtos));
		page.setPageNumber(pageable.getPageNumber() - 1);
		page.setSize(pageable.getPageSize());
		page.setTotalPages((int)Math.ceil((double)totalRecords/pageable.getPageSize()));
		return page;
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
