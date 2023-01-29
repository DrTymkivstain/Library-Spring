package com.example.LibrarySpring.service;

import com.example.LibrarySpring.dto.UserDTO;
import com.example.LibrarySpring.model.User;

import java.util.List;

public interface UserService {
    void registerUser(UserDTO userDTO);

    void updateUser(UserDTO userDTO);

    void deleteUser(UserDTO userDTO);

    void banUser(UserDTO userDTO);

    void unBanUser(UserDTO userDTO);

    List<User> getAllUsers();

    User findUserById(Long id);
}
