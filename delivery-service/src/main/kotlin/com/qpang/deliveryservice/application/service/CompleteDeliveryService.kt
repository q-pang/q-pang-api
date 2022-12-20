package com.qpang.deliveryservice.application.service

import com.qpang.deliveryservice.application.port.`in`.CompleteDeliveryUseCase
import com.qpang.deliveryservice.application.port.out.persistence.DeliveryPersistencePort
import com.qpang.deliveryservice.application.service.exception.DeliveryIdNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CompleteDeliveryService(
    private val deliveryPersistencePort: DeliveryPersistencePort
) : CompleteDeliveryUseCase {
    @Transactional
    override fun command(command: CompleteDeliveryUseCase.CompleteDeliveryCommand): CompleteDeliveryUseCase.CompleteDeliveryInfo {
        val savedDelivery = deliveryPersistencePort.findById(command.id)
        savedDelivery ?: throw DeliveryIdNotFoundException(command.id)
        savedDelivery.completeDelivery()

        return CompleteDeliveryUseCase.CompleteDeliveryInfo.from(savedDelivery)
    }

}