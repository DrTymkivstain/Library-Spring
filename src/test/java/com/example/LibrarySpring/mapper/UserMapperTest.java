package com.example.LibrarySpring.mapper;

import com.example.LibrarySpring.dto.UserDTO;
import com.example.LibrarySpring.model.Role;
import com.example.LibrarySpring.model.Status;
import com.example.LibrarySpring.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

 class UserMapperTest {

    @Test
    public void testToUserDTO() {
        User user = new User();
        user.setUserId(1L);
        user.setEmail("@gmail");
        user.setPhone("099-047-0010");
        user.setUsername("john_doe");
        user.setPassword("password");
        user.setRole(Role.ADMIN_ROLE);
        user.setStatus(Status.ACTIVE);

        UserDTO userDTO = UserMapper.MAPPER.toUserDTO(user);

        assertEquals(user.getUserId(), userDTO.getUserId());
        assertEquals(user.getUsername(), userDTO.getUsername());
        assertEquals(user.getPassword(), userDTO.getPassword());
    }

    @Test
    public void testToUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(1L);
        userDTO.setPhone("099-047-36-11");
        userDTO.setEmail("@gmail");
        userDTO.setUsername("john_doe");
        userDTO.setPassword("password");

        User user = UserMapper.MAPPER.toUser(userDTO);

        assertEquals(userDTO.getUserId(), user.getUserId());
        assertEquals(userDTO.getUsername(), user.getUsername());
        assertEquals(userDTO.getPassword(), user.getPassword());
        assertEquals(user.getRole(), Role.USER_ROLE);
        assertEquals(user.getStatus(), Status.ACTIVE);
    }
}