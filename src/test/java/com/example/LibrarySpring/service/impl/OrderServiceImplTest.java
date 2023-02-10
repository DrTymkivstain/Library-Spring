package com.example.LibrarySpring.service.impl;

import com.example.LibrarySpring.dto.OrderDTO;
import com.example.LibrarySpring.model.Book;
import com.example.LibrarySpring.model.BookAvailabilityStatus;
import com.example.LibrarySpring.model.Order;
import com.example.LibrarySpring.model.User;
import com.example.LibrarySpring.repository.BookRepository;
import com.example.LibrarySpring.repository.OrderRepository;
import com.example.LibrarySpring.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class OrderServiceImplTest {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private OrderServiceImpl orderService;
    private Order order;
    private OrderDTO orderDTO;
    private User user;
    private Book book;

    @BeforeEach
    public void configTestData() {
        order = new Order();
        user = new User();
        book = new Book();
        book.setName("book");
        user.setUsername("user");
        order.setBook(book);
        order.setActive(true);
        order.setStartDate(LocalDate.now());
        order.setUser(user);
        order.getBook().setStatus(BookAvailabilityStatus.AVAILABLE);
        orderDTO = OrderDTO.builder()
                .id(1L)
                .bookName("book")
                .userName("user")
                .build();
    }


    @Test
    void createOrder() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(bookRepository.findByName(book.getName())).thenReturn(Optional.of(book));
        when(orderRepository.save(order)).thenReturn(order);
        orderService.createOrder(orderDTO);
        verify(orderRepository).save(order);
        assertEquals(order.getBook().getStatus(), BookAvailabilityStatus.NOT_AVAILABLE);
    }

    @Test
    void closeOrder() {
        when(orderRepository.findById(orderDTO.getId())).thenReturn(Optional.of(order));
        orderService.closeOrder(orderDTO);
        assertFalse(order.isActive());
        assertEquals(order.getBook().getStatus(), BookAvailabilityStatus.AVAILABLE);
    }

    @Test
    void getAllOrders() {
        order.setId(1L);
        orderDTO.setStartDate(order.getStartDate().toString());
        orderDTO.setEndDate(order.getEndDate().toString());
        when(orderRepository.findAll()).thenReturn(List.of(order));
        assertEquals(List.of(orderDTO), orderService.getAllOrders());
        verify(orderRepository).findAll();
    }

    @Test
    void getActiveOrders() {
        order.setId(1L);
        orderDTO.setStartDate(order.getStartDate().toString());
        orderDTO.setEndDate(order.getEndDate().toString());
        when(orderRepository.findAllByActiveIsTrue()).thenReturn(List.of(order));
        assertEquals(List.of(orderDTO), orderService.getActiveOrders());
        verify(orderRepository).findAllByActiveIsTrue();
    }

    @Test
    void getPassiveOrders() {
        order.setId(1L);
        orderDTO.setStartDate(order.getStartDate().toString());
        orderDTO.setEndDate(order.getEndDate().toString());
        when(orderRepository.findAllByActiveIsFalse()).thenReturn(List.of());
        assertEquals(List.of(), orderService.getPassiveOrders());
        verify(orderRepository).findAllByActiveIsFalse();
    }

    @Test
    void findActiveOrdersByUser() {
        order.setId(1L);
        orderDTO.setStartDate(order.getStartDate().toString());
        orderDTO.setEndDate(order.getEndDate().toString());
        when(orderRepository.findAllByActiveIsTrueAndUser_Username(user.getUsername())).thenReturn(List.of(order));
        assertEquals(List.of(orderDTO), orderService.findActiveOrdersByUser(user.getUsername()));
        verify(orderRepository).findAllByActiveIsTrueAndUser_Username(user.getUsername());
    }

    @Test
    void findPassiveOrdersByUser() {
        order.setId(1L);
        orderDTO.setStartDate(order.getStartDate().toString());
        orderDTO.setEndDate(order.getEndDate().toString());
        when(orderRepository.findAllByActiveIsFalseAndUser_Username(user.getUsername())).thenReturn(List.of());
        assertEquals(List.of(), orderService.findPassiveOrdersByUser(user.getUsername()));
        verify(orderRepository).findAllByActiveIsFalseAndUser_Username(user.getUsername());
    }

    @Test
    void getAllOrdersByUser() {
        order.setId(1L);
        orderDTO.setStartDate(order.getStartDate().toString());
        orderDTO.setEndDate(order.getEndDate().toString());
        when(orderRepository.findAllByUser_Username(user.getUsername())).thenReturn(List.of(order));
        assertEquals(List.of(orderDTO), orderService.getAllOrdersByUser(user.getUsername()));
        verify(orderRepository).findAllByUser_Username(user.getUsername());
    }
}