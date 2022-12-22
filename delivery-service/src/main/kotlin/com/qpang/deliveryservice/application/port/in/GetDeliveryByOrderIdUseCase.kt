package com.qpang.deliveryservice.application.port.`in`

import com.qpang.deliveryservice.domain.Delivery
import java.time.LocalDateTime

interface GetDeliveryByOrderIdUseCase {
    fun command(command: GetDeliveryByOrderIdCommand): GetDeliveryByOrderIdInfo

    data class GetDeliveryByOrderIdCommand(
        val orderId: String
    )

    data class GetDeliveryByOrderIdInfo(
        val id: String,
        val orderId: String,
        val status: Delivery.DeliveryStatus,
        val arrivalAt: LocalDateTime?
    ) {
        companion object {
            fun from(delivery: Delivery): GetDeliveryByOrderIdInfo = GetDeliveryByOrderIdInfo(
                id = delivery.getId(),
                orderId = delivery.orderId,
                status = delivery.status,
                arrivalAt = delivery.arrivalAt
            )
        }
    }
}