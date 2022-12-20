package com.qpang.orderservice.application.port.`in`.dto

import com.qpang.orderservice.domain.Payment

data class PaymentCommand(
    val type: Payment.PaymentMethodType,
    val company: Payment.CardCompany,
    val number: String,
    val username: String
)
