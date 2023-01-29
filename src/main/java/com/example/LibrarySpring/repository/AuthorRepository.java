package com.example.LibrarySpring.repository;

import com.example.LibrarySpring.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
