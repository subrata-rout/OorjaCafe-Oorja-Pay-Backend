package com.oorjacafe.oorjapay.controller;


import com.oorjacafe.oorjapay.dto.UserDTO;
import com.oorjacafe.oorjapay.dto.UserResponseDTO;
import com.oorjacafe.oorjapay.entity.User;
import com.oorjacafe.oorjapay.response.ApiResponse;
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

//    @PostMapping
//    public User createUser( @RequestBody @Valid UserDTO userDTO){
//        return userService.createUser(userDTO);
//    }
    @PostMapping
    public ApiResponse<UserResponseDTO> createUser(@RequestBody @Valid UserDTO userDTO){

        UserResponseDTO user=userService.createUser(userDTO);
        return new ApiResponse<>(
                true,
                "User created successfully",
                user
        );
    }

    @GetMapping
    public  ApiResponse<List<UserResponseDTO>> getAllUsers(){
        return new ApiResponse<>(
                true,
                "User fetched successfully",
                userService.getAllUsers()
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponseDTO> getUserById(@PathVariable Long id){
        return new ApiResponse<>(
                true,
                "User fetched successfully",
                userService.getUserById(id)
        );
    }

    @PutMapping("/{id}")
    public ApiResponse<UserResponseDTO> updateUser(@PathVariable Long id,@RequestBody @Valid UserDTO userDTO){
        return new ApiResponse<>(
                true,
                "User updated successfully",
                userService.updateUser(id, userDTO)
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse <String> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return new ApiResponse<>(
                true,
                "User deleted Successfully",
                null
        );
    }
}
