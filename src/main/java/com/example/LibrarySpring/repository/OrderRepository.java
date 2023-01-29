package com.example.LibrarySpring.repository;

import com.example.LibrarySpring.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
