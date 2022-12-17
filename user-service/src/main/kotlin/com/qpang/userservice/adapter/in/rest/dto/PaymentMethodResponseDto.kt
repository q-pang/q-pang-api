package com.qpang.userservice.adapter.`in`.rest.dto

import com.qpang.userservice.domain.PaymentMethod

data class PaymentMethodResponseDto(
    val type: PaymentMethod.PaymentMethodType,
    val company: PaymentMethod.CardCompany
)
