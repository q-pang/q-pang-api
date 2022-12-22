package com.qpang.deliveryservice.adapter.`in`.event

import com.qpang.deliveryservice.adapter.`in`.event.dto.CancelOrderEvent
import com.qpang.deliveryservice.application.port.`in`.CancelDeliveryByOrderIdUseCase
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class CancelOrderEventAdapter(
    private val cancelDeliveryByOrderIdUseCase: CancelDeliveryByOrderIdUseCase
) {
    @KafkaListener(topics = ["cancel-order"])
    fun consume(event: CancelOrderEvent) {
        cancelDeliveryByOrderIdUseCase.command(CancelDeliveryByOrderIdUseCase.CancelDeliveryByOrderIdCommand(event.orderId))
    }
}