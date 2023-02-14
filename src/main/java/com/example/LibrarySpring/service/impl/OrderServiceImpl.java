package com.example.LibrarySpring.service.impl;

import com.example.LibrarySpring.dto.OrderDTO;
import com.example.LibrarySpring.exception.CustomException;
import com.example.LibrarySpring.model.Book;
import com.example.LibrarySpring.model.BookAvailabilityStatus;
import com.example.LibrarySpring.model.Order;
import com.example.LibrarySpring.repository.BookRepository;
import com.example.LibrarySpring.repository.OrderRepository;
import com.example.LibrarySpring.repository.UserRepository;
import com.example.LibrarySpring.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            UserRepository userRepository,
                            BookRepository bookRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        log.info("create order {}", orderDTO);
        Order order = buildAndActivateOrder(orderDTO);
        return this.buildOrderDTO(orderRepository.save(order));
    }

    private Order buildAndActivateOrder(OrderDTO orderDTO) {
        Order order = orderRepository.findById(orderDTO.getId()).orElse(new Order());
        order.setBook(bookRepository.findByName(orderDTO.getBookName()).orElseThrow(
                () -> new CustomException("book not found")));
        if (order.getBook().getStatus().equals(BookAvailabilityStatus.NOT_AVAILABLE)) {
            throw new CustomException("book is not available");
        }
        order.setActive(true);
        order.setStartDate(LocalDate.now());
        order.setUser(userRepository.findByUsername(orderDTO.getUserName()).orElseThrow(
                () -> new UsernameNotFoundException("user not found")));
        order.getBook().setStatus(BookAvailabilityStatus.NOT_AVAILABLE);
        return order;
    }

    @Scheduled(cron = "0 0 0 * * * ")
    public void AutoCloser() {
        orderRepository.findAllByActiveIsTrue()
                .forEach(this::checkOrderExpiration);
    }
    private void checkOrderExpiration(Order order) {
        LocalDate date = LocalDate.now();
        if (date.isEqual(order.getEndDate()) || date.isAfter(order.getEndDate())) {
            this.disActivateOrder(order);
            this.unLockBook(order.getBook());
        }
    }

    @Override
    public void closeOrder(OrderDTO orderDTO) {
        log.info("close order {}", orderDTO);
        Order order = orderRepository.findById(orderDTO.getId()).orElseThrow(
                () -> new CustomException("order not found"));
        disActivateOrder(order);
        unLockBook(order.getBook());

    }

    @Override
    public List<OrderDTO> getAllOrders() {
        return orderRepository
                .findAll()
                .stream()
                .map(this::buildOrderDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getActiveOrders() {
        return orderRepository
                .findAllByActiveIsTrue()
                .stream()
                .map(this::buildOrderDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getPassiveOrders() {
        return orderRepository
                .findAllByActiveIsFalse()
                .stream()
                .map(this::buildOrderDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> findActiveOrdersByUser(String userName) {
        log.info("active orders by username {}", userName);
        return orderRepository.findAllByActiveIsTrueAndUser_Username(userName)
                .stream()
                .map(this::buildOrderDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> findPassiveOrdersByUser(String userName) {
        log.info("active orders by username {}", userName);
        return orderRepository.findAllByActiveIsFalseAndUser_Username(userName)
                .stream()
                .map(this::buildOrderDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getAllOrdersByUser(String userName) {
        return orderRepository.findAllByUser_Username(userName)
                .stream()
                .map(this::buildOrderDTO)
                .collect(Collectors.toList());
    }

    private OrderDTO buildOrderDTO(Order order) {
        return OrderDTO.builder()
                .id(order.getId())
                .endDate(order.getEndDate().toString())
                .startDate(order.getStartDate().toString())
                .bookName(order.getBook().getName())
                .userName(order.getUser().getUsername())
                .build();
    }

    private void disActivateOrder(Order order) {
        order.setActive(false);
    }

    private void unLockBook(Book book) {
        book.setStatus(BookAvailabilityStatus.AVAILABLE);
    }

}
