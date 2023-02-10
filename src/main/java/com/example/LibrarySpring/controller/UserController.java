package com.example.LibrarySpring.controller;

import com.example.LibrarySpring.dto.UserDTO;
import com.example.LibrarySpring.model.User;
import com.example.LibrarySpring.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController( UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/reg")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        log.info("create user {}", userDTO);
        return userService.registerUser(userDTO);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('developers:read')")
    public UserDTO updateUser(@RequestBody UserDTO userDTO) {
        log.info("update user {}", userDTO);
        return userService.updateUser(userDTO);

    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('developers:read')")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable Long id) {
        log.info("delete user with id {}", id);
        userService.deleteUser(id);
    }
    @PostMapping("/ban/{id}")
    @PreAuthorize("hasAuthority('developers:write')")
    public UserDTO banUser(@PathVariable Long id ) {
        log.info("ban user with id {}", id);
        return userService.banUser(id);
    }

    @PostMapping("/unban/{id}")
    @PreAuthorize("hasAuthority('developers:write')")
    public UserDTO unBanUser(@PathVariable Long id) {
        log.info("unban user with id {}", id);
        return userService.unBanUser(id);
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('developers:write')")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasAuthority('developers:write')")
    public User getUserById(@PathVariable(name = "id") Long id) {
        return userService.findUserById(id);
    }



}
