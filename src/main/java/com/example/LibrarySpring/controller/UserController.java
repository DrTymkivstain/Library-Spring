package com.example.LibrarySpring.controller;

import com.example.LibrarySpring.dto.BookDTO;
import com.example.LibrarySpring.dto.UserDTO;
import com.example.LibrarySpring.model.User;
import com.example.LibrarySpring.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(@Qualifier(value = "UserServiceImpl") UserService userService) {
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
    @PreAuthorize("hasAuthority('developers:read')")
    public UserDTO updateUser(UserDTO userDTO) {
        log.info("update user {}", userDTO);
        userService.updateUser(userDTO);
        return  userDTO;
    }

    @DeleteMapping("delete")
    @PreAuthorize("hasAuthority('developers:read')")
    public UserDTO deleteUser(UserDTO userDTO) {
        log.info("delete user {}", userDTO);
        userService.deleteUser(userDTO);
        return  userDTO;
    }
    @PostMapping("/ban")
    @PreAuthorize("hasAuthority('developers:write')")
    public UserDTO banUser(UserDTO userDTO) {
        log.info("ban user {}", userDTO);
        userService.banUser(userDTO);
        return userDTO;
    }

    @PostMapping("/unban")
    @PreAuthorize("hasAuthority('developers:write')")
    public UserDTO unBanUser(UserDTO userDTO) {
        log.info("unban user {}", userDTO);
        userService.unBanUser(userDTO);
        return userDTO;
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('developers:write')")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("user/{id}")
    @PreAuthorize("hasAuthority('developers:write')")
    public User getUserById(@PathVariable(name = "id") Long id) {
        return userService.findUserById(id);
    }



}
