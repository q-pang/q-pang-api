package com.qpang.userservice.application.port.`in`.usecase

interface DeletePaymentMethodUseCase {
    fun command(command: DeletePaymentMethodCommand): DeletePaymentMethodInfo

    data class DeletePaymentMethodCommand(
        val username: String,
        val paymentMethodId: String
    )

    data class DeletePaymentMethodInfo(
        val id: String,
        val username: String
    )
}