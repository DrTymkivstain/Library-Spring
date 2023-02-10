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
    public UserDTO registerUser(UserDTO userDTO) {
        User user = new User(userDTO);
        return this.buildUserDTOFromUser(userRepository.save(user));
    }

    private UserDTO buildUserDTOFromUser(User user) {
        return UserDTO.builder()
                .phone(user.getPhone())
                .email(user.getEmail())
                .username(user.getUsername())
                .password(user.getPassword())
                .id(user.getUserId())
                .build();
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        log.info("update user with id {}", userDTO.getId());
        User user = userRepository
                .findById(userDTO.getId())
                .orElseThrow(() -> new ClassCastException("user not found"));
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setPassword(userDTO.getPassword());
        user.setUsername(userDTO.getUsername());
        return this.buildUserDTOFromUser(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        log.info("delete user with id {}", id);
        userRepository.deleteById(id);
    }

    @Override
    public UserDTO banUser(Long id) {
        log.info("ban user with id {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("not found user with id {}" + id));
        user.setStatus(Status.BANNED);
        return this.buildUserDTOFromUser(userRepository.save(user));
    }

    @Override
    public UserDTO unBanUser(Long id) {
        log.info("unban user with id {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("not found user with id {}" + id));
        user.setStatus(Status.ACTIVE);
        return this.buildUserDTOFromUser(userRepository.save(user));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new CustomException("user not found"));
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
