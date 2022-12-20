package com.qpang.orderservice.application.port.`in`.dto

import com.qpang.orderservice.domain.Payment

data class PaymentInfo(
    val type: Payment.PaymentMethodType,
    val company: Payment.CardCompany,
    val number: String,
    val username: String
) {
    companion object {
        fun from(payment: Payment): PaymentInfo = PaymentInfo(
            type = payment.type,
            company = payment.company,
            number = payment.number,
            username = payment.username,
        )
    }
}