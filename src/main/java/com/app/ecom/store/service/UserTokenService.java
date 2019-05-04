package com.app.ecom.store.service;

import com.app.ecom.store.model.User;
import com.app.ecom.store.model.UserToken;

public interface UserTokenService {

    String createUserToken(User user);

    UserToken getUserToken(String token);

}
