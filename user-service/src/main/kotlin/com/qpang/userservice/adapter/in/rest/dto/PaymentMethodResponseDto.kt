package com.qpang.userservice.adapter.`in`.rest.dto

import com.qpang.userservice.domain.PaymentMethod

data class PaymentMethodResponseDto(
    val id: String,
    val type: PaymentMethod.PaymentMethodType,
    val company: PaymentMethod.CardCompany,
    val number: String
)
