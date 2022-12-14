package com.qpang.deliveryservice.adapter.out.persistence

import com.qpang.deliveryservice.application.port.out.persistence.DeliveryPersistencePort
import com.qpang.deliveryservice.domain.Delivery
import com.qpang.deliveryservice.infrastructure.repository.DeliveryRepository
import org.springframework.stereotype.Component

@Component
class DeliveryPersistenceAdapter(
    private val deliveryRepository: DeliveryRepository
) : DeliveryPersistencePort {
    override fun save(delivery: Delivery): Delivery = deliveryRepository.save(delivery)
    override fun findById(id: String): Delivery? = deliveryRepository.findDeliveryById(id)
    override fun findByOrderId(orderId: String): Delivery? = deliveryRepository.findDeliveryByOrderId(orderId)
}