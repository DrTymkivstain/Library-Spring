package com.example.LibrarySpring.service;

import com.example.LibrarySpring.dto.UserDTO;
import com.example.LibrarySpring.model.User;

import java.util.List;

public interface UserService {
    UserDTO registerUser(UserDTO userDTO);

    UserDTO updateUser(UserDTO userDTO);

    void deleteUser(Long id);

    UserDTO banUser(Long id);

    UserDTO unBanUser(Long id);

    List<User> getAllUsers();

    User findUserById(Long id);
}
