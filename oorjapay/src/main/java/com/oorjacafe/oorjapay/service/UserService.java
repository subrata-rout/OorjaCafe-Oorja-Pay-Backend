package com.oorjacafe.oorjapay.service;


import com.oorjacafe.oorjapay.entity.User;
import com.oorjacafe.oorjapay.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(User user){
        return userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("User not found"));
    }

    public User updateUser(Long id, User user){
        User existingUser= userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("User not found"));

        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());

        return userRepository.save(existingUser);
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
}
