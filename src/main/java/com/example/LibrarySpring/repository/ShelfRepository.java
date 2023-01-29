package com.example.LibrarySpring.repository;

import com.example.LibrarySpring.model.Shelf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShelfRepository extends JpaRepository<Shelf, Long> {
    Optional<Shelf> findByBookIsNull();
}
