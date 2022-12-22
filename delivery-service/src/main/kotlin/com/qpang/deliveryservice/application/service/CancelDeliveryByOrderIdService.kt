package com.qpang.deliveryservice.application.service

import com.qpang.deliveryservice.application.port.`in`.CancelDeliveryByOrderIdUseCase
import com.qpang.deliveryservice.application.port.out.persistence.DeliveryPersistencePort
import com.qpang.deliveryservice.application.service.exception.DeliveryIdNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CancelDeliveryByOrderIdService(
    private val deliveryPersistencePort: DeliveryPersistencePort
) : CancelDeliveryByOrderIdUseCase {
    @Transactional
    override fun command(command: CancelDeliveryByOrderIdUseCase.CancelDeliveryByOrderIdCommand): CancelDeliveryByOrderIdUseCase.CancelDeliveryByOrderIdInfo {
        val savedDelivery = deliveryPersistencePort.findByOrderId(command.orderId)
        savedDelivery ?: throw DeliveryIdNotFoundException(command.orderId)
        savedDelivery.cancelDelivery()

        return CancelDeliveryByOrderIdUseCase.CancelDeliveryByOrderIdInfo.from(savedDelivery)
    }

}