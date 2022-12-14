package com.qpang.deliveryservice.application.service

import com.qpang.deliveryservice.application.port.`in`.RegisterDeliveryUseCase
import com.qpang.deliveryservice.application.port.out.persistence.DeliveryPersistencePort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RegisterDeliveryService(
    private val deliveryPersistencePort: DeliveryPersistencePort
) : RegisterDeliveryUseCase {
    @Transactional
    override fun command(command: RegisterDeliveryUseCase.RegisterDeliveryCommand): RegisterDeliveryUseCase.RegisterDeliveryInfo {
        val savedDelivery = deliveryPersistencePort.save(command.toEntity())

        return RegisterDeliveryUseCase.RegisterDeliveryInfo.from(savedDelivery)
    }

}