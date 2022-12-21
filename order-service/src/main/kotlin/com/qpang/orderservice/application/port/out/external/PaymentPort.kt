package com.qpang.orderservice.application.port.out.external

import com.qpang.orderservice.domain.Payment

interface PaymentPort {
    fun payment(paymentRequestDto: ExternalPaymentRequestDto): String
    fun cancelPayment(externalPaymentId: String)

    data class ExternalPaymentRequestDto(
        val name: String,
        val type: Payment.PaymentMethodType,
        val company: Payment.CardCompany,
        val number: String,
        val amount: Long
    )
}