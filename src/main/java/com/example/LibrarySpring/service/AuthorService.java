package com.example.LibrarySpring.service;

import com.example.LibrarySpring.model.Author;

import java.util.Set;

public interface AuthorService {
    Set<Author> mapAuthorArrayIntoAuthorSet(Author[] authors);
}
