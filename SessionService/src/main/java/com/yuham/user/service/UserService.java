package com.yuham.user.service;

import com.yuham.user.enetity.User;

import java.util.List;

/**
 * @author yuhan
 * @date 05.06.2021 - 11:51
 * @purpose
 */
public interface UserService {

    public List<User> findAll();

    public User findByUserUid(int userUid);

    public String CreateUser(User user);

    public Boolean Login(User user);

    User getUser(String loginName);
}
