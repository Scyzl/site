package com.scy.service.impl;

import com.scy.dao.UserRepository;
import com.scy.po.User;
import com.scy.service.UserService;
import com.scy.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author Scy
 * @Date 2020/8/7 13:16
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, MD5Utils.getMD5(password));

        return user;
    }
}
