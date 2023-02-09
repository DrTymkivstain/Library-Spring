package com.example.LibrarySpring.service.impl;

import com.example.LibrarySpring.model.Author;
import com.example.LibrarySpring.model.Book;
import com.example.LibrarySpring.model.Tag;
import com.example.LibrarySpring.repository.AuthorRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class AuthorServiceImplTest {
    @InjectMocks
    private AuthorServiceImpl authorService;
    @Mock
    private AuthorRepository authorRepository;

    @Test
    void mapAuthorArrayIntoAuthorSet() {
        Book firstBook = new Book();
        Book secondBook = new Book();
        Set<Author> tagsSet = Set.of(new Author(1L, "first", Set.of(firstBook))
                ,new Author(1L, "second", Set.of(secondBook)));
        Author[] authors = new Author[] {new Author(1L, "first", Set.of(firstBook))
                ,new Author(1L, "second", Set.of(secondBook))};
        assertEquals(tagsSet, authorService.mapAuthorArrayIntoAuthorSet(authors));
    }

    @Test
    void getAllAuthors() {
        when(authorRepository.findAll()).thenReturn(List.of());
        assertEquals(authorService.getAllAuthors(), List.of());
        verify(authorRepository).findAll();
    }
}