package com.qpang.userservice.application.port.`in`.usecase

import com.qpang.userservice.application.port.`in`.usecase.info.PaymentMethodInfo

interface MyInfoUseCase {
    fun command(command: MyInfoCommand): MyInfoInfo

    data class MyInfoCommand(
        val username: String
    )

    data class MyInfoInfo(
        val id: String,
        val username: String,
        val name: String,
        val paymentMethods: List<PaymentMethodInfo>
    )
}