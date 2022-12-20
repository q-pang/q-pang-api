package com.qpang.orderservice.application.port.out.persistence

import com.qpang.orderservice.domain.Order

interface OrderPersistencePort {
    fun saveOrder(order: Order): Order
}