package com.qpang.deliveryservice.application.port.`in`

import com.qpang.deliveryservice.domain.Delivery
import java.time.LocalDateTime

interface CancelDeliveryByOrderIdUseCase {
    fun command(command: CancelDeliveryByOrderIdCommand): CancelDeliveryByOrderIdInfo

    data class CancelDeliveryByOrderIdCommand(
        val orderId: String
    )

    data class CancelDeliveryByOrderIdInfo(
        val id: String,
        val orderId: String,
        val status: Delivery.DeliveryStatus,
        val arrivalAt: LocalDateTime?
    ) {
        companion object {
            fun from(delivery: Delivery): CancelDeliveryByOrderIdInfo = CancelDeliveryByOrderIdInfo(
                id = delivery.getId(),
                orderId = delivery.orderId,
                status = delivery.status,
                arrivalAt = delivery.arrivalAt
            )
        }
    }
}