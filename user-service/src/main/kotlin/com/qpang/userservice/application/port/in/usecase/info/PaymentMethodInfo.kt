package com.qpang.userservice.application.port.`in`.usecase.info

import com.qpang.userservice.domain.PaymentMethod

data class PaymentMethodInfo(
    val type: PaymentMethod.PaymentMethodType,
    val company: PaymentMethod.CardCompany,
    val number: String
)