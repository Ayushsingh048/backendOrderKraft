package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
