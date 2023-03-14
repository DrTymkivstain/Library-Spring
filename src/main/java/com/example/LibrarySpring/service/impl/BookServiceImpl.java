package com.example.LibrarySpring.service.impl;

import com.example.LibrarySpring.dto.BookDTO;
import com.example.LibrarySpring.dto.FilterDTO;
import com.example.LibrarySpring.exception.CustomException;
import com.example.LibrarySpring.model.*;
import com.example.LibrarySpring.repository.BookRepository;
import com.example.LibrarySpring.repository.ShelfRepository;
import com.example.LibrarySpring.repository.TagRepository;
import com.example.LibrarySpring.service.AuthorService;
import com.example.LibrarySpring.service.BookService;
import com.example.LibrarySpring.service.TagService;
import com.example.LibrarySpring.util.Validator;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public BookDTO addNewBook(BookDTO bookDTO) {
        Validator.checkNewBook(bookDTO);
        log.info("created book {}", bookDTO);
        Shelf shelf = shelfRepository.findByBookIsNull().orElse(new Shelf());
        Book book = buildBookFromBookDTO(bookDTO);
        book.getTags().stream().forEach(tag -> tag.getBooks().add(book));
        book.getAuthors().stream().forEach(author -> author.getBooks().add(book));
        book.setShelf(shelf);
        book.setStatus(BookAvailabilityStatus.AVAILABLE);
        shelf.setBook(book);
        shelfRepository.save(shelf);
        return this.buildBookDTO(bookRepository.save(book));
    }

    private Book buildBookFromBookDTO(BookDTO bookDTO) {

        return Book.builder()
                .id(bookDTO.getId())
                .name(bookDTO.getName())
                .tags(tagService.mapTagArrayIntoTagSet(bookDTO.getTags()))
                .authors(authorService.mapAuthorArrayIntoAuthorSet(bookDTO.getAuthors()))
                .build();
    }

    @Override
    public BookDTO updateBook(BookDTO bookDTO) {
        log.info("update book with id {}", bookDTO.getId());
        Book book = bookRepository
                .findById(bookDTO.getId())
                .orElseThrow(() -> new CustomException("book not found"));
        book.setAuthors(authorService.mapAuthorArrayIntoAuthorSet(bookDTO.getAuthors()));
        book.setTags(tagService.mapTagArrayIntoTagSet(bookDTO.getTags()));
        return this.buildBookDTO(bookRepository.save(book));
    }



    @Override
    public void deleteBookById(Long id) {
        log.info("delete book with id {}", id);
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDTO> getAvailableBooks(Pageable pageable) {
        return bookRepository.findAllByStatus(BookAvailabilityStatus.AVAILABLE, pageable)
                .stream()
                .map(this::buildBookDTO)
                .collect(Collectors.toList());
    }

    private BookDTO buildBookDTO(Book book) {
        return BookDTO.builder()
                .id(book.getId())
                .authors(getArrayOfAuthors(book))
                .tags(getArrayOfTags(book))
                .name(book.getName())
                .build();
    }


    private Tag[] getArrayOfTags(Book book) {
        return  book.getTags().toArray(new Tag[book.getTags().size()]);
    }

    private Author[] getArrayOfAuthors(Book book) {
        return book.getAuthors().toArray(new Author[book.getAuthors().size()]);
    }

    @Override
    public List<BookDTO> getAvailableBookDTOsByFilter(FilterDTO filterDTO, Pageable pageable) {
        return getAvailableBooksByFilter(filterDTO, pageable)
                .stream()
                .map(this::buildBookDTO)
                .collect(Collectors.toList());

    }

    private List<Book> getAvailableBooksByFilter(FilterDTO filterDTO, Pageable pageable) {
        return bookRepository.getAvailableBooksByFilter(
                        filterDTO.getName(),
                filterDTO.getAuthors(),
                filterDTO.getTags(),
                pageable);
    }
}
