package com.example.LibrarySpring.service;

import com.example.LibrarySpring.dto.OrderDTO;

import java.util.List;

public interface OrderService {
    void createOrder(OrderDTO orderDTO);

    void closeOrder(OrderDTO orderDTO);

    List<OrderDTO> getAllOrders();

    List<OrderDTO> getActiveOrders();

    List<OrderDTO> getPassiveOrders();

    List<OrderDTO> findActiveOrdersByUser(String userName);

    List<OrderDTO> findPassiveOrdersByUser(String userName);

    List<OrderDTO> getAllOrdersByUser(String userName);
}
