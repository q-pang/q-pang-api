package com.qpang.deliveryservice.application.service

import com.qpang.deliveryservice.application.port.`in`.CancelDeliveryUseCase
import com.qpang.deliveryservice.application.port.out.persistence.DeliveryPersistencePort
import com.qpang.deliveryservice.application.service.exception.DeliveryIdNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CancelDeliveryService(
    private val deliveryPersistencePort: DeliveryPersistencePort
) : CancelDeliveryUseCase {
    @Transactional
    override fun command(command: CancelDeliveryUseCase.CancelDeliveryCommand): CancelDeliveryUseCase.CancelDeliveryInfo {
        val savedDelivery = deliveryPersistencePort.findById(command.id)
        savedDelivery ?: throw DeliveryIdNotFoundException(command.id)
        savedDelivery.cancelDelivery()

        return CancelDeliveryUseCase.CancelDeliveryInfo.from(savedDelivery)
    }

}