package com.example.LibrarySpring.controller;

import com.example.LibrarySpring.dto.BookDTO;
import com.example.LibrarySpring.dto.UserDTO;
import com.example.LibrarySpring.model.User;
import com.example.LibrarySpring.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/reg")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO createUser(UserDTO userDTO) {
        log.info("create user {}", userDTO);
        userService.registerUser(userDTO);
        return userDTO;
    }

    @PutMapping("/update")
    public UserDTO updateUser(UserDTO userDTO) {
        log.info("update user {}", userDTO);
        userService.updateUser(userDTO);
        return  userDTO;
    }

    @DeleteMapping("delete")
    public UserDTO deleteUser(UserDTO userDTO) {
        log.info("delete user {}", userDTO);
        userService.deleteUser(userDTO);
        return  userDTO;
    }
    @PostMapping("/ban")
    public UserDTO banUser(UserDTO userDTO) {
        log.info("ban user {}", userDTO);
        userService.banUser(userDTO);
        return userDTO;
    }

    @PostMapping("/unban")
    public UserDTO unBanUser(UserDTO userDTO) {
        log.info("unban user {}", userDTO);
        userService.unBanUser(userDTO);
        return userDTO;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("user/{id}")
    public User getUserById(@PathVariable(name = "id") Long id) {
        return userService.findUserById(id);
    }



}
