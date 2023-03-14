package com.example.LibrarySpring.service.impl;

import com.example.LibrarySpring.model.Author;
import com.example.LibrarySpring.model.Book;
import com.example.LibrarySpring.repository.AuthorRepository;
import com.example.LibrarySpring.repository.ShelfRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
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
    @Autowired
    private ShelfRepository shelfRepository;

    @Test
    void mapAuthorArrayIntoAuthorSet() {
        Book firstBook = new Book();
        Book secondBook = new Book();
        Author firstAuthor = new Author(1L, "first", Set.of(firstBook));
        Author secondAuthor = new Author(2L, "second", Set.of(secondBook));
        when(authorRepository.findById(firstAuthor.getId())).thenReturn(Optional.of(firstAuthor));
        when(authorRepository.findById(secondAuthor.getId())).thenReturn(Optional.of(secondAuthor));
        Set<Author> tagsSet = Set.of(firstAuthor,secondAuthor);
        Author[] authors = new Author[] {firstAuthor, secondAuthor};
        assertEquals(tagsSet, authorService.mapAuthorArrayIntoAuthorSet(authors));
    }

    @Test
    void getAllAuthors() {
        when(authorRepository.findAll()).thenReturn(List.of());
        assertEquals(authorService.getAllAuthors(), List.of());
        verify(authorRepository).findAll();
    }
}