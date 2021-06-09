package com.yuham.user.service;

import com.yuham.user.enetity.User;
import com.yuham.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * @author yuhan
 * @date 05.06.2021 - 11:58
 * @purpose
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByUserUid(int userUid) {
        return userRepository.findByUserUid(userUid)
                .orElseThrow(()-> new EntityNotFoundException(String.format("User not found for userUid %s", userUid)));
    }

    @Override
    public String CreateUser(User user) {
        int userUid = (int) (Math.random() * 100);
        user.setUserUid(userUid);
        String flag = null;
        User user1 = userRepository.save(user);
        if (user1.getId() > 0) {
            flag = "success";
        } else {
            flag = "error";
        }
//        List<User> users = userRepository.findAll();
//        for (User u:users)
//            if (user.getName().equals(u.getName())) {
//                flag = "error";
//            } else {
//
//            }
        return flag;
    }

    @Override
    public Boolean Login(User user) {
        boolean flag = false;
        String name = user.getName();
        String password = user.getPassword();
        List<User> users = userRepository.findAll();
        for (User u:users){
            if (name.equals(u.getName())){
                if (password.equals(u.getPassword())){
                    flag = true;
                }
            }
        }
        return flag;
    }

    @Override
    public User getUser(String name) {
//        User user = new User();
        return userRepository.findByUserName(name)
                .orElseThrow(()-> new EntityNotFoundException(String.format("User not found for name %s", name)));

    }


}
