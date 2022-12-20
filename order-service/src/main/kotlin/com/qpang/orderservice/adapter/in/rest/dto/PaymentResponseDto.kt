package com.qpang.orderservice.adapter.`in`.rest.dto

import com.qpang.orderservice.application.port.`in`.dto.PaymentInfo
import com.qpang.orderservice.domain.Payment

data class PaymentResponseDto(
    val type: Payment.PaymentMethodType,
    val company: Payment.CardCompany,
    val number: String,
    val username: String
) {
    companion object {
        fun from(info: PaymentInfo): PaymentResponseDto = PaymentResponseDto(
            type = info.type,
            company = info.company,
            number = info.number,
            username = info.username
        )
    }
}