package com.example.LibrarySpring.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserDTO {
    private String username;
    private String email;
    private String phone;
    private String password;
}