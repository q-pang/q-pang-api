package com.qpang.deliveryservice.application.port.`in`

import com.qpang.deliveryservice.domain.Delivery
import java.time.LocalDateTime

interface GetDeliveryUseCase {
    fun command(command: GetDeliveryCommand): GetDeliveryInfo

    data class GetDeliveryCommand(
        val id: String
    )

    data class GetDeliveryInfo(
        val id: String,
        val orderId: String,
        val status: Delivery.DeliveryStatus,
        val arrivalAt: LocalDateTime?
    ) {
        companion object {
            fun from(delivery: Delivery): GetDeliveryInfo = GetDeliveryInfo(
                id = delivery.getId(),
                orderId = delivery.orderId,
                status = delivery.status,
                arrivalAt = delivery.arrivalAt
            )
        }
    }
}