package com.qpang.orderservice.adapter.`in`.rest.dto

import com.qpang.orderservice.application.port.`in`.dto.PaymentCommand
import com.qpang.orderservice.domain.Payment

data class PaymentRequestDto(
    val type: Payment.PaymentMethodType,
    val company: Payment.CardCompany,
    val number: String,
    val username: String
) {
    fun toCommand(): PaymentCommand = PaymentCommand(
        type = type,
        company = company,
        number = number,
        username = username
    )
}
/*
"type": "",
"company": "",
"number": "",
"username": ""
 */