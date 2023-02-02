package com.example.LibrarySpring.service;

import com.example.LibrarySpring.dto.BookDTO;
import com.example.LibrarySpring.dto.FilterDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    void addNewBook(BookDTO bookDTO);

    void updateBook(BookDTO bookDTO);

    void deleteBook(BookDTO bookDTO);

    List<BookDTO> getAvailableBooks(Pageable pageable);

    List<BookDTO> getAvailableBookDTOsByFilter(FilterDTO filterDTO, Pageable pageable);
}
