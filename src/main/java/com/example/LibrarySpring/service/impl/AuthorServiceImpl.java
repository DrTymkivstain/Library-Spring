package com.example.LibrarySpring.service.impl;

import com.example.LibrarySpring.model.Author;
import com.example.LibrarySpring.repository.AuthorRepository;
import com.example.LibrarySpring.service.AuthorService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Set<Author> mapAuthorArrayIntoAuthorSet(Author[] authors) {
        return Arrays.stream(authors)
                .collect(Collectors.toSet());
    }

    @Override
    public List<String> getAllAuthors() {
        return authorRepository
                .findAll()
                .stream()
                .map(author -> author.toString())
                .collect(Collectors.toList());
    }
}
