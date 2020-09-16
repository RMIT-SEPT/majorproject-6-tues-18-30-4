package com.sai.oabs.user.service;

import com.sai.oabs.user.entity.User;
import com.sai.oabs.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findByUserNameAndPwd(String username,String password){
        return userRepository.findByUsernameAndPassword(username,password);
    }

    public void save(User user){
        userRepository.save(user);
    }

    public void delete(Long id){
        userRepository.deleteById(id);
    }

    public User findById(Long id){
        return userRepository.findById(id).orElse(null);
    }

}
