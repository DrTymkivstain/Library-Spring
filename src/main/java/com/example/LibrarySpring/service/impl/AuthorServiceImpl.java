package com.example.LibrarySpring.service.impl;

import com.example.LibrarySpring.model.Author;
import com.example.LibrarySpring.service.AuthorService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {
    @Override
    public Set<Author> mapAuthorArrayIntoAuthorSet(Author[] authors) {
        return Arrays.stream(authors)
                .collect(Collectors.toSet());
    }
}
