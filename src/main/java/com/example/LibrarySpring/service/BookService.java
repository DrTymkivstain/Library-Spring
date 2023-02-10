package com.example.LibrarySpring.service;

import com.example.LibrarySpring.dto.BookDTO;
import com.example.LibrarySpring.dto.FilterDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    BookDTO addNewBook(BookDTO bookDTO);

    BookDTO updateBook(BookDTO bookDTO);

    void deleteBookById(Long id);

    List<BookDTO> getAvailableBooks(Pageable pageable);

    List<BookDTO> getAvailableBookDTOsByFilter(FilterDTO filterDTO, Pageable pageable);
}
