package com.example.LibrarySpring.model;

import com.example.LibrarySpring.dto.UserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String username;
    private String password;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private Status status;


    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Role role;

    private String email;
    private String phone;

    public User() {
    }


    public User(String username, String password, Status status, Role role, String email, String phone) {
        this.username = username;
        this.password = password;
        this.status = status;
        this.role = role;
        this.email = email;
        this.phone = phone;
    }

    public User(UserDTO userDTO) {
        this.username = userDTO.getUsername();
        this.password = userDTO.getPassword();
        this.phone = userDTO.getPhone();
        this.email = userDTO.getEmail();
        this.role = Role.USER_ROLE;
        this.status = Status.ACTIVE;
    }
}