package com.example.LibrarySpring.repository;

import com.example.LibrarySpring.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Object> findByName(String name);
}
