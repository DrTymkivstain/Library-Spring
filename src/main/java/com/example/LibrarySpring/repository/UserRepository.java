package com.example.LibrarySpring.repository;

import com.example.LibrarySpring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
