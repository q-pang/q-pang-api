package com.qpang.orderservice.application.port.out.external

import com.qpang.orderservice.domain.Payment

interface PaymentPort {
    fun payment(paymentRequestDto: PaymentRequestDto)

    data class PaymentRequestDto(
        val name: String,
        val type: Payment.PaymentMethodType,
        val company: Payment.CardCompany,
        val number: String,
        val amount: Long
    )
}