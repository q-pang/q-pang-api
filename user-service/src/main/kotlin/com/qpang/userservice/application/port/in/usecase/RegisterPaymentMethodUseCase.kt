package com.qpang.userservice.application.port.`in`.usecase

import com.qpang.userservice.domain.PaymentMethod

interface RegisterPaymentMethodUseCase {
    fun command(command: RegisterPaymentMethodCommand): RegisterPaymentMethodInfo

    data class RegisterPaymentMethodCommand(
        val username: String,
        val type: PaymentMethod.PaymentMethodType,
        val company: PaymentMethod.CardCompany,
        val number: String
    )

    data class RegisterPaymentMethodInfo(
        val id: String,
        val username: String,
        val type: PaymentMethod.PaymentMethodType,
        val company: PaymentMethod.CardCompany,
        val number: String
    )
}