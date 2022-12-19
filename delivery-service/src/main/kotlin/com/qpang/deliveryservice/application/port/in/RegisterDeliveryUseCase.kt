package com.qpang.deliveryservice.application.port.`in`

import com.qpang.deliveryservice.domain.Delivery
import java.time.LocalDateTime

interface RegisterDeliveryUseCase {
    fun command(command: RegisterDeliveryCommand): RegisterDeliveryInfo

    data class RegisterDeliveryCommand(
        val userId: String,
        val orderId: String,
    ) {
        fun toEntity() = Delivery(userId = userId, orderId = orderId)
    }

    data class RegisterDeliveryInfo(
        val id: String,
        val userId: String,
        val orderId: String,
        val status: Delivery.DeliveryStatus,
        val arrivalAt: LocalDateTime?
    ) {
        companion object {
            fun from(delivery: Delivery): RegisterDeliveryInfo = RegisterDeliveryInfo(
                id = delivery.getId(),
                userId = delivery.userId,
                orderId = delivery.orderId,
                status = delivery.status,
                arrivalAt = delivery.arrivalAt
            )
        }
    }
}