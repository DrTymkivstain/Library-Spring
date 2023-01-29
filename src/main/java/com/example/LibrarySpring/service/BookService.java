package com.example.LibrarySpring.service;

import com.example.LibrarySpring.dto.BookDTO;

public interface BookService {
    void addNewBook(BookDTO bookDTO);

    void updateBook(BookDTO bookDTO);

    void deleteBook(BookDTO bookDTO);
}
