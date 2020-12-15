package com.wp.api;

import com.wp.bean.User;

public interface UserService {
    
    User findByUserId(Long id);
    
}
