package com.qpang.deliveryservice.infrastructure.repository

import com.qpang.deliveryservice.domain.Delivery
import org.springframework.data.jpa.repository.JpaRepository

interface DeliveryRepository : JpaRepository<Delivery, String> {
    fun findDeliveryById(id: String): Delivery?
    fun findDeliveryByOrderId(orderId: String): Delivery?
}