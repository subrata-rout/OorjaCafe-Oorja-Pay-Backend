package com.oorjacafe.oorjapay.controller;


import com.oorjacafe.oorjapay.dto.LoginDTO;
import com.oorjacafe.oorjapay.dto.RegisterDTO;
import com.oorjacafe.oorjapay.dto.UserResponseDTO;
import com.oorjacafe.oorjapay.entity.User;
import com.oorjacafe.oorjapay.response.ApiResponse;
import com.oorjacafe.oorjapay.security.JwtUtil;
import com.oorjacafe.oorjapay.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    //Login

    //    public ApiResponse<UserResponseDTO> login(@RequestBody @Valid LoginDTO loginDTO){
//        User user=userService.login(loginDTO);
//        return new ApiResponse<>(
//                true,
//                "Login successful",
//                userService.mapToResponse(user)
//        );
//    }

    @PostMapping("/login")
    public ApiResponse<String> login(@RequestBody @Valid LoginDTO loginDTO) {

        return userService.login(loginDTO);
    }
    @PostMapping("/register")
    public String register(@RequestBody RegisterDTO dto){
        return userService.register(dto);
    }

}
