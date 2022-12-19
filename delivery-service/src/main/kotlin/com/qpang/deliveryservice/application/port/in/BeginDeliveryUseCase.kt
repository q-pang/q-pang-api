package com.qpang.deliveryservice.application.port.`in`

import com.qpang.deliveryservice.domain.Delivery
import java.time.LocalDateTime

interface BeginDeliveryUseCase {
    fun command(command: BeginDeliveryCommand): BeginDeliveryInfo

    data class BeginDeliveryCommand(
        val id: String
    )

    data class BeginDeliveryInfo(
        val id: String,
        val orderId: String,
        val status: Delivery.DeliveryStatus,
        val arrivalAt: LocalDateTime?
    ) {
        companion object {
            fun from(delivery: Delivery): BeginDeliveryInfo = BeginDeliveryInfo(
                id = delivery.getId(),
                orderId = delivery.orderId,
                status = delivery.status,
                arrivalAt = delivery.arrivalAt
            )
        }
    }
}