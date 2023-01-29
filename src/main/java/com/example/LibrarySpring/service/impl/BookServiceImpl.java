package com.example.LibrarySpring.service.impl;

import com.example.LibrarySpring.dto.BookDTO;
import com.example.LibrarySpring.exception.CustomException;
import com.example.LibrarySpring.model.Book;
import com.example.LibrarySpring.model.BookAvailabilityStatus;
import com.example.LibrarySpring.model.Shelf;
import com.example.LibrarySpring.repository.BookRepository;
import com.example.LibrarySpring.repository.ShelfRepository;
import com.example.LibrarySpring.repository.TagRepository;
import com.example.LibrarySpring.service.AuthorService;
import com.example.LibrarySpring.service.BookService;
import com.example.LibrarySpring.service.TagService;
import com.example.LibrarySpring.util.Validator;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final ShelfRepository shelfRepository;
    private final AuthorService authorService;
    private final TagService tagService;
    private final TagRepository tagRepository;

    public BookServiceImpl(BookRepository bookRepository,
                           ShelfRepository shelfRepository,
                           AuthorService authorService,
                           TagService tagService,
                           TagRepository tagRepository) {
        this.bookRepository = bookRepository;
        this.shelfRepository = shelfRepository;
        this.authorService = authorService;
        this.tagService = tagService;
        this.tagRepository = tagRepository;
    }


    @Override
    @Transactional
    public void addNewBook(BookDTO bookDTO) {
        Validator.checkNewBook(bookDTO);
        log.info("created book {}", bookDTO);
        Shelf shelf = shelfRepository.findByBookIsNull().orElse(new Shelf());
        Book book = buildBookFromBookDTO(bookDTO);
        book.setShelf(shelf);
        book.setStatus(BookAvailabilityStatus.AVAILABLE);
        shelf.setBook(book);
        bookRepository.save(book);
        shelfRepository.save(shelf);

    }

    private Book buildBookFromBookDTO(BookDTO bookDTO) {

        return Book.builder()
                .name(bookDTO.getName())
                .tags(tagService.mapTagArrayIntoTagSet(bookDTO.getTags()))
                .authors(authorService.mapAuthorArrayIntoAuthorSet(bookDTO.getAuthors()))
                .build();
    }

    @Override
    public void updateBook(BookDTO bookDTO) {
        log.info("update book with id {}", bookDTO.getId());
        bookRepository.save(getUpdatedBook(bookDTO));
    }

    private Book getUpdatedBook(BookDTO bookDTO) {
        Book book = bookRepository
                .findById(bookDTO.getId())
                .orElseThrow(() -> new CustomException("book not found"));
        book.setAuthors(authorService.mapAuthorArrayIntoAuthorSet(bookDTO.getAuthors()));
        book.setTags(tagService.mapTagArrayIntoTagSet(bookDTO.getTags()));
        return book;
    }

    @Override
    public void deleteBook(BookDTO bookDTO) {
        log.info("delete book with id {}", bookDTO.getId());
        bookRepository.deleteById(bookDTO.getId());
    }
}
