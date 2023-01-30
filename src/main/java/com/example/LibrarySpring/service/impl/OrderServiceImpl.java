package com.example.LibrarySpring.service.impl;

import com.example.LibrarySpring.dto.OrderDTO;
import com.example.LibrarySpring.model.Order;
import com.example.LibrarySpring.service.OrderService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public void createOrder(OrderDTO orderDTO) {
        Order order = new Order();
        //business logic of creation order
        checkOrderExpiration(order);
    }

    private void checkOrderExpiration(Order order) {
        LocalDate date = LocalDate.now();
        if(date.isEqual(order.getEndDate()) || date.isAfter(order.getEndDate())) {
            this.closeOrder(order);
        }
    }

    private void closeOrder(Order order) {
        ///close order logic
    }
}
