package com.qpang.orderservice.infrastructure.repository

import com.qpang.orderservice.domain.Order
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<Order, String> {
    fun findOrderById(id: String): Order?
}