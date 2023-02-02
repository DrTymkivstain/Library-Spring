package com.example.LibrarySpring.repository;

import com.example.LibrarySpring.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByActiveIsTrue();

    List<Order> findAllByActiveIsFalse();

    List<Order> findAllByActiveIsTrueAndUser_Username(String userName);

    List<Order> findAllByActiveIsFalseAndUser_Username(String userName);

    List<Order> findAllByUser_Username(String userName);
}
