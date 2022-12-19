package com.qpang.deliveryservice.application.port.`in`

import com.qpang.deliveryservice.domain.Delivery
import java.time.LocalDateTime

interface CancelDeliveryUseCase {
    fun command(command: CancelDeliveryCommand): CancelDeliveryInfo

    data class CancelDeliveryCommand(
        val id: String
    )

    data class CancelDeliveryInfo(
        val id: String,
        val orderId: String,
        val status: Delivery.DeliveryStatus,
        val arrivalAt: LocalDateTime?
    ) {
        companion object {
            fun from(delivery: Delivery): CancelDeliveryInfo = CancelDeliveryInfo(
                id = delivery.getId(),
                orderId = delivery.orderId,
                status = delivery.status,
                arrivalAt = delivery.arrivalAt
            )
        }
    }
}