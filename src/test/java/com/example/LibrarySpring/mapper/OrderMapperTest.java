package com.example.LibrarySpring.mapper;

import com.example.LibrarySpring.dto.OrderDTO;
import com.example.LibrarySpring.model.Book;
import com.example.LibrarySpring.model.Order;
import com.example.LibrarySpring.model.User;
import org.junit.jupiter.api.Test;


import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
class OrderMapperTest {

    @Test
    void toOrder() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(1L);
        orderDTO.setBookName("book");
        orderDTO.setUserName("user");
        orderDTO.setStartDate(String.valueOf(LocalDate.now()));
        orderDTO.setEndDate(String.valueOf(LocalDate.now().plusMonths(1)));

        Order order = OrderMapper.MAPPER.toOrder(orderDTO);

        assertEquals(order.getId(), orderDTO.getId());
        assertEquals(String.valueOf(order.getStartDate()), orderDTO.getStartDate());
        assertEquals(String.valueOf(order.getEndDate()), orderDTO.getEndDate());
    }

    @Test
    void toOrderDTO() {
        Order order = new Order();
        User user = new User();
        Book book = new Book();
        user.setUsername("user");
        book.setName("book");
        order.setUser(user);
        order.setId(1L);
        order.setBook(book);
        order.setStartDate(LocalDate.now());

        OrderDTO orderDTO = OrderMapper.MAPPER.toOrderDTO(order);

        assertEquals(order.getBook().getName(), orderDTO.getBookName());
        assertEquals(order.getUser().getUsername(), orderDTO.getUserName());
        assertEquals(String.valueOf(order.getStartDate()), orderDTO.getStartDate());
        assertEquals(String.valueOf(order.getEndDate()), orderDTO.getEndDate());
        assertEquals(order.getId(), orderDTO.getId());
    }
}