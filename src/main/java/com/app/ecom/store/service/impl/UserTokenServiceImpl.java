package com.app.ecom.store.service.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.app.ecom.store.model.User;
import com.app.ecom.store.model.UserToken;
import com.app.ecom.store.repository.UserTokenRepository;
import com.app.ecom.store.service.UserTokenService;

@Service
public class UserTokenServiceImpl implements UserTokenService {

    @Inject
    private UserTokenRepository userTokenRepository;

    @Override
    public String createUserToken(User user) {
        String token = UUID.randomUUID().toString();
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, 24 * 60);
        Date expiryDate = new Date(cal.getTime().getTime());
        UserToken userToken = new UserToken();
        userToken.setToken(token);
        userToken.setExpiryDate(expiryDate);
        userToken.setUser(user);
        userTokenRepository.save(userToken);
        return token;
    }

    @Override
    public UserToken getUserToken(String token) {
        return userTokenRepository.findByToken(token);
    }
}
