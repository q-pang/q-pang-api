package com.qpang.deliveryservice.application.service

import com.qpang.deliveryservice.application.port.`in`.BeginDeliveryUseCase
import com.qpang.deliveryservice.application.port.out.persistence.DeliveryPersistencePort
import com.qpang.deliveryservice.application.service.exception.DeliveryIdNotFoundException
import org.springframework.stereotype.Service

@Service
class BeginDeliveryService(
    private val deliveryPersistencePort: DeliveryPersistencePort
) : BeginDeliveryUseCase {
    override fun command(command: BeginDeliveryUseCase.BeginDeliveryCommand): BeginDeliveryUseCase.BeginDeliveryInfo {
        val savedDelivery = deliveryPersistencePort.findById(command.id)
        savedDelivery ?: throw DeliveryIdNotFoundException(command.id)
        savedDelivery.beginDelivery()

        return BeginDeliveryUseCase.BeginDeliveryInfo.from(savedDelivery)
    }

}