package com.qpang.orderservice.adapter.out.persistence

import com.qpang.orderservice.application.port.out.persistence.OrderPersistencePort
import com.qpang.orderservice.domain.Order
import com.qpang.orderservice.infrastructure.repository.OrderRepository
import org.springframework.stereotype.Component

@Component
class OrderPersistenceAdapter(
    private val orderRepository: OrderRepository
) : OrderPersistencePort {
    override fun saveOrder(order: Order): Order = orderRepository.save(order)
    override fun findOrderById(id: String): Order? = orderRepository.findOrderById(id)
}