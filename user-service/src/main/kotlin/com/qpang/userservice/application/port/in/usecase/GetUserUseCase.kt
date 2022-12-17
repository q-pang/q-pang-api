package com.qpang.userservice.application.port.`in`.usecase

import com.qpang.userservice.application.port.`in`.usecase.info.PaymentMethodInfo

interface GetUserUseCase {
    fun command(command: GetUserCommand): GetUserInfo

    data class GetUserCommand(
        val username: String
    )

    data class GetUserInfo(
        val id: String,
        val username: String,
        val name: String,
        val paymentMethods: List<PaymentMethodInfo>
    )
}