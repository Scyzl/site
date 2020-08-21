package com.scy.dao;

import com.scy.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author Scy
 * @Date 2020/8/7 13:17
 * @Version 1.0
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsernameAndPassword(String username, String password);

}
