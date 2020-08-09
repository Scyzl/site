package com.scy.service;

import com.scy.po.User;

/**
 * 用户登录业务层接口
 *
 * @Author Scy
 * @Date 2020/8/7 13:14
 * @Version 1.0
 */
public interface UserService {

    User checkUser(String username, String password);
}
