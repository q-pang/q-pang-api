package com.qpang.orderservice.application.port.out.rest.dto

import java.time.LocalDateTime

data class DeliveryResponseDto(
    val id: String,
    val orderId: String,
    val status: DeliveryStatus,
    val arrivalAt: LocalDateTime?
) {
    enum class DeliveryStatus(
        val statusName: String
    ) {
        AWAITING("AWAITING"), INPROGRESS("INPROGRESS"), COMPLETED("COMPLETED"), CANCELLED("CANCELLED")
    }
}