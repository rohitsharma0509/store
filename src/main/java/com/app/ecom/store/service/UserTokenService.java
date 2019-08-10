package com.app.ecom.store.service;

import com.app.ecom.store.dto.UserTokenDto;
import com.app.ecom.store.model.User;

public interface UserTokenService {

    String createUserToken(User user);
    
    UserTokenDto getUserToken(String token);

}
