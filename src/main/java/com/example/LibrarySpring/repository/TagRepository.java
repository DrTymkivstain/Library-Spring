package com.example.LibrarySpring.repository;

import com.example.LibrarySpring.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
