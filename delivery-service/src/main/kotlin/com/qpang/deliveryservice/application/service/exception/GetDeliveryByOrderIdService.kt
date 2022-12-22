package com.qpang.deliveryservice.application.service.exception

import com.qpang.deliveryservice.application.port.`in`.GetDeliveryByOrderIdUseCase
import com.qpang.deliveryservice.application.port.out.persistence.DeliveryPersistencePort
import org.springframework.stereotype.Service

@Service
class GetDeliveryByOrderIdService(
    private val deliveryPersistencePort: DeliveryPersistencePort
) : GetDeliveryByOrderIdUseCase {
    override fun command(command: GetDeliveryByOrderIdUseCase.GetDeliveryByOrderIdCommand): GetDeliveryByOrderIdUseCase.GetDeliveryByOrderIdInfo {
        val savedDelivery = deliveryPersistencePort.findByOrderId(command.orderId)
        savedDelivery ?: throw DeliveryIdNotFoundException(command.orderId)

        return GetDeliveryByOrderIdUseCase.GetDeliveryByOrderIdInfo.from(savedDelivery)
    }
}