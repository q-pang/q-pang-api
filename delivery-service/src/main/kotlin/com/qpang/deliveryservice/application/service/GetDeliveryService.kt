package com.qpang.deliveryservice.application.service

import com.qpang.deliveryservice.application.port.`in`.GetDeliveryUseCase
import com.qpang.deliveryservice.application.port.out.persistence.DeliveryPersistencePort
import com.qpang.deliveryservice.application.service.exception.DeliveryIdNotFoundException
import org.springframework.stereotype.Service

@Service
class GetDeliveryService(
    private val deliveryPersistencePort: DeliveryPersistencePort
) : GetDeliveryUseCase {
    override fun command(command: GetDeliveryUseCase.GetDeliveryCommand): GetDeliveryUseCase.GetDeliveryInfo {
        val savedDelivery = deliveryPersistencePort.findById(command.id)
        savedDelivery ?: throw DeliveryIdNotFoundException(command.id)

        return GetDeliveryUseCase.GetDeliveryInfo.from(savedDelivery)
    }
}