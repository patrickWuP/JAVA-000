package com.wp.rpcserver.impl;

import com.wp.api.UserService;
import com.wp.bean.User;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service(value = "com.wp.api.UserService")
public class UserServiceImpl implements UserService {

    @Override
    public User findByUserId(Long id) {
        User user = new User();
        user.setId(id);
        user.setTel("17001279697");
        user.setNickName("wp");
        user.setCreateTime(new Date());
        user.setModifyTime(new Date());
        return user;
    }
}
