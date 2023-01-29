package com.example.LibrarySpring.service.impl;

import com.example.LibrarySpring.dto.UserDTO;
import com.example.LibrarySpring.exception.CustomException;
import com.example.LibrarySpring.model.Role;
import com.example.LibrarySpring.model.Status;
import com.example.LibrarySpring.model.User;
import com.example.LibrarySpring.repository.UserRepository;
import com.example.LibrarySpring.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerUser(UserDTO userDTO) {
        User user = new User(userDTO);
    }

    @Override
    public void updateUser(UserDTO userDTO) {
        log.info("update user with id {}", userDTO.getId());
        userRepository.save(getUpdatedUser(userDTO));
    }

    @Override
    public void deleteUser(UserDTO userDTO) {
        log.info("delete user with id {}", userDTO.getId());
        userRepository.deleteById(userDTO.getId());
    }

    @Override
    public void banUser(UserDTO userDTO) {
        log.info("ban user with id {}", userDTO);
        userRepository.findByUsername(userDTO.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("not found user with username {}" + userDTO.getUsername()))
                .setStatus(Status.BANNED);
    }

    @Override
    public void unBanUser(UserDTO userDTO) {
        log.info("unban user with id {}", userDTO);
        userRepository.findByUsername(userDTO.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("not found user with username {}" + userDTO.getUsername()))
                .setStatus(Status.ACTIVE);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ClassCastException("ser not found"));
    }


    private User getUpdatedUser(UserDTO userDTO) {
        User user = userRepository
                .findById(userDTO.getId())
                .orElseThrow(() -> new ClassCastException("user not found"));
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setPassword(userDTO.getPassword());
        user.setUsername(userDTO.getUsername());
        return user;
    }
    private void saveNewUserAsAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("user already exist");
        }
    }
    @PostConstruct
    private void postConstruct() {
        User admin = new User("admin","admin", Status.ACTIVE, Role.ADMIN_ROLE, "@","1");
        this.saveNewUserAsAdmin(admin);
    }

    public User loadUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException("user not found"));
    }
}
