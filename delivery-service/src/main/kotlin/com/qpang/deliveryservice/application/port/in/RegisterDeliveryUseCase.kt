package com.qpang.deliveryservice.application.port.`in`

import com.qpang.deliveryservice.domain.Delivery
import java.time.LocalDateTime

interface RegisterDeliveryUseCase {
    fun command(command: RegisterDeliveryCommand): RegisterDeliveryInfo

    data class RegisterDeliveryCommand(
        val orderId: String
    ) {
        fun toEntity() = Delivery(orderId)
    }

    data class RegisterDeliveryInfo(
        val id: String,
        val orderId: String,
        val status: Delivery.DeliveryStatus,
        val arrivalAt: LocalDateTime?
    ) {
        companion object {
            fun from(delivery: Delivery): RegisterDeliveryInfo = RegisterDeliveryInfo(
                id = delivery.getId(),
                orderId = delivery.orderId,
                status = delivery.status,
                arrivalAt = delivery.arrivalAt
            )
        }
    }
}