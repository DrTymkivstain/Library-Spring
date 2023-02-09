package com.example.LibrarySpring.service.impl;

import com.example.LibrarySpring.dto.UserDTO;
import com.example.LibrarySpring.exception.CustomException;
import com.example.LibrarySpring.model.Status;
import com.example.LibrarySpring.model.User;
import com.example.LibrarySpring.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void registerUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("test");
        User user = new User(userDTO);
        when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(user);
        assertEquals(userDTO, userService.registerUser(userDTO));
        verify(userRepository).save(user);
    }

    @Test
    void updateUserIfUserExist() {
        User user = new User();
        user.setUserId(89L);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(89L);
        User newUser = new User();
        when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
        userService.updateUser(userDTO);
        verify(userRepository).findById(userDTO.getId());
    }

    @Test()
    void updateUserIfUserNotExist() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(8L);
        when(userRepository.findById(anyLong())).thenThrow(CustomException.class);
        CustomException thrown = assertThrows(CustomException.class, () -> userService.updateUser(userDTO));
        assertTrue(Objects.equals(thrown.getClass(), CustomException.class));
    }
    @Test
    void deleteUser() {
            User user = new User();
            UserDTO userDTO = new UserDTO();
            userDTO.setId(1L);
            user.setUserId(1L);
            when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
            userService.deleteUser(userDTO);
            verify(userRepository).deleteById(userDTO.getId());
        }
    @Test
    void deleteUserIfNotExist() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(8L);
        when(userRepository.findById(anyLong())).thenThrow(CustomException.class);
        CustomException thrown = assertThrows(CustomException.class, () -> userService.updateUser(userDTO));
        assertTrue(Objects.equals(thrown.getClass(), CustomException.class));
    }

    @Test
    void banUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("user");
        User user = new User();
        user.setUsername("user");
        user.setStatus(Status.ACTIVE);
        when(userRepository.findByUsername(userDTO.getUsername())).thenReturn(Optional.of(user));
        userService.banUser(userDTO);
        assertEquals(userRepository.findByUsername(user.getUsername()).get().getStatus(), Status.BANNED);
        verify(userRepository, times(2)).findByUsername(user.getUsername());
    }

    @Test
    void unBanUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("user");
        User user = new User();
        user.setUsername("user");
        user.setStatus(Status.BANNED);
        when(userRepository.findByUsername(userDTO.getUsername())).thenReturn(Optional.of(user));
        userService.unBanUser(userDTO);
        assertEquals(userRepository.findByUsername(user.getUsername()).get().getStatus(), Status.ACTIVE);
        verify(userRepository, times(2)).findByUsername(user.getUsername());
    }

    @Test
    void getAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of());
        assertEquals(userService.getAllUsers(), List.of());
        verify(userRepository).findAll();
    }

    @Test
    void findUserById() {
        User user = new User();
        user.setUserId(1L);
        when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
        assertEquals(user, userService.findUserById(user.getUserId()));
        verify(userRepository).findById(user.getUserId());
    }

    @Test
    void findUserByIdIfNotExist() {
        User user = new User();
        user.setUserId(1L);
        when(userRepository.findById(anyLong())).thenThrow(CustomException.class);
        CustomException thrown = assertThrows(CustomException.class, () -> userService.findUserById(user.getUserId()));
        assertTrue(Objects.equals(thrown.getClass(), CustomException.class));
    }

    @Test
    void loadUserByUsername() {
    }
}