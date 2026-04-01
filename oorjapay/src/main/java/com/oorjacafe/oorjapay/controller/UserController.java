package com.oorjacafe.oorjapay.controller;


import com.oorjacafe.oorjapay.dto.UserDTO;
import com.oorjacafe.oorjapay.entity.User;
import com.oorjacafe.oorjapay.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public User createUser( @RequestBody @Valid UserDTO userDTO){
        return userService.createUser(userDTO);
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id,@RequestBody @Valid UserDTO userDTO){
        return userService.updateUser(id, userDTO);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return "User deleted Successfully";
    }
}
