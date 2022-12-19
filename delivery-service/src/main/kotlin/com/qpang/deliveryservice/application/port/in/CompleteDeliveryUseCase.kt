package com.qpang.deliveryservice.application.port.`in`

import com.qpang.deliveryservice.domain.Delivery
import java.time.LocalDateTime

interface CompleteDeliveryUseCase {
    fun command(command: CompleteDeliveryCommand): CompleteDeliveryInfo

    data class CompleteDeliveryCommand(
        val id: String
    )

    data class CompleteDeliveryInfo(
        val id: String,
        val orderId: String,
        val status: Delivery.DeliveryStatus,
        val arrivalAt: LocalDateTime?
    ) {
        companion object {
            fun from(delivery: Delivery): CompleteDeliveryInfo = CompleteDeliveryInfo(
                id = delivery.getId(),
                orderId = delivery.orderId,
                status = delivery.status,
                arrivalAt = delivery.arrivalAt
            )
        }
    }
}