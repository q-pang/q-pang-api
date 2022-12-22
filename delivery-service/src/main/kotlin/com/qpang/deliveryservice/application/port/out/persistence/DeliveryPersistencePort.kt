package com.qpang.deliveryservice.application.port.out.persistence

import com.qpang.deliveryservice.domain.Delivery

interface DeliveryPersistencePort {
    fun save(delivery: Delivery): Delivery
    fun findById(id: String): Delivery?
    fun findByOrderId(orderId: String): Delivery?
}