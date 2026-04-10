package com.oorjacafe.oorjapay.service;


import com.oorjacafe.oorjapay.dto.LoginDTO;
import com.oorjacafe.oorjapay.dto.RegisterDTO;
import com.oorjacafe.oorjapay.dto.UserDTO;
import com.oorjacafe.oorjapay.dto.UserResponseDTO;
import com.oorjacafe.oorjapay.entity.User;
import com.oorjacafe.oorjapay.exception.UserNotFoundException;
import com.oorjacafe.oorjapay.repository.UserRepository;
import com.oorjacafe.oorjapay.response.ApiResponse;
import com.oorjacafe.oorjapay.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
//    @Autowired
//    private UserRepository userRepository;

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    //constructor injection
    public UserService(UserRepository userRepository , BCryptPasswordEncoder passwordEncoder
    ,JwtService jwtService
                       ) {
        this.userRepository = userRepository;
        this.passwordEncoder=passwordEncoder;
        this.jwtService=jwtService;
    }

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

        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

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


    //Login Method
    public ApiResponse<String> login(LoginDTO loginDTO) {

        System.out.println("LOGIN EMAIL FROM REQUEST: " + loginDTO.getEmail());

        Optional<User> userOptional = userRepository.findByEmail(loginDTO.getEmail());

        System.out.println("USER FOUND IN DB? " + userOptional.isPresent());

        if (userOptional.isEmpty()) {
            return new ApiResponse<>(false, "User not found", null);
        }

        User user = userOptional.get();

        System.out.println("EMAIL IN DB: " + user.getEmail());

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            return new ApiResponse<>(false, "Invalid password", null);
        }

        String token = jwtService.generateToken(user.getEmail());

        return new ApiResponse<>(true, "Login successful", token);
    }


    public String register(RegisterDTO dto){
        //if already exists
        if(userRepository.findByEmail(dto.getEmail()).isPresent()){
            return "User already exists";
        }
        User user=new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());

        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        userRepository.save(user);
        return "User registered successfully";

    }
}
