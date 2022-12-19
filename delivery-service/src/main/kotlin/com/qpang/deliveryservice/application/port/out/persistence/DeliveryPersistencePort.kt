package com.qpang.deliveryservice.application.port.out.persistence

import com.qpang.deliveryservice.domain.Delivery

interface DeliveryPersistencePort {
    fun save(toEntity: Delivery): Delivery
    fun findById(id: String): Delivery?
}