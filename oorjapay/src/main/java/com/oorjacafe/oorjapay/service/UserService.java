package com.oorjacafe.oorjapay.service;


import com.oorjacafe.oorjapay.dto.UserDTO;
import com.oorjacafe.oorjapay.dto.UserResponseDTO;
import com.oorjacafe.oorjapay.entity.User;
import com.oorjacafe.oorjapay.exception.UserNotFoundException;
import com.oorjacafe.oorjapay.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

//    public User createUser(User user){
//        return userRepository.save(user);
//    }
//    public User createUser(UserDTO userDTO){
//        User user=new User();
//        user.setName(userDTO.getName());
//        user.setEmail(userDTO.getEmail());
//
//        return userRepository.save(user);
//
//    }

    //Entity--> Response DTO Mapping
    public UserResponseDTO mapToResponse(User user){
        UserResponseDTO dto=new UserResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        return dto;
    }

    public UserResponseDTO createUser(UserDTO userDTO){
        //DTO->Entity
        User user=new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());

        //save to DB
        User savedUser=userRepository.save(user);

        //Entity-> ResponseDTO
        return mapToResponse(savedUser);
    }

//    public List<User> getAllUsers(){
//        return userRepository.findAll();
//    }

    //Rturn ResponseDTO list
    public List<UserResponseDTO>getAllUsers(){
        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse) //convert each entity->DTO
                .toList();
    }

//    public User getUserById(Long id){
//        return userRepository.findById(id)
//                .orElseThrow(()-> new RuntimeException("User not found"));
//    }
    //return responseDTO
    public UserResponseDTO getUserById(Long id){
        User user=userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("User not found"));
        return mapToResponse(user);
    }


    //return ResponseDTO
    public UserResponseDTO updateUser(Long id, UserDTO userDTO){
        User existingUser= userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("User not found"));

        existingUser.setName(userDTO.getName());
        existingUser.setEmail(userDTO.getEmail());

        //return userRepository.save(existingUser);
        User updareUser=userRepository.save(existingUser);
        return mapToResponse(updareUser);
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }


}
