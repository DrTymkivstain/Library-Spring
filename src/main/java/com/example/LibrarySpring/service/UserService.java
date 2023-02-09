package com.example.LibrarySpring.service;

import com.example.LibrarySpring.dto.UserDTO;
import com.example.LibrarySpring.model.User;

import java.util.List;

public interface UserService {
    UserDTO registerUser(UserDTO userDTO);

    UserDTO updateUser(UserDTO userDTO);

    UserDTO deleteUser(UserDTO userDTO);

    UserDTO banUser(UserDTO userDTO);

    UserDTO unBanUser(UserDTO userDTO);

    List<User> getAllUsers();

    User findUserById(Long id);
}
