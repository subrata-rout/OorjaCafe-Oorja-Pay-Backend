package com.oorjacafe.oorjapay.dto;

import jakarta.validation.constraints.NotBlank;

public class UserDTO {
    @NotBlank(message= "Name cannot be empty")
    private String name;
    @NotBlank(message="Invalid email format")
    @NotBlank(message="Email cannot be empty")
    private String email;

    //Getters & Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
