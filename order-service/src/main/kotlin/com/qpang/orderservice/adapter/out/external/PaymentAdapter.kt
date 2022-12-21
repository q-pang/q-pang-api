package com.qpang.orderservice.adapter.out.external

import com.qpang.orderservice.adapter.out.external.exception.FailedCancelPaymentException
import com.qpang.orderservice.application.port.out.external.PaymentPort
import com.qpang.orderservice.infrastructure.external.PaymentModule
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class PaymentAdapter(
    private val paymentModule: PaymentModule
) : PaymentPort {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)
    override fun payment(paymentRequestDto: PaymentPort.ExternalPaymentRequestDto): String {
        return paymentModule.payment(
            paymentRequestDto.name,
            paymentRequestDto.type.typeName,
            paymentRequestDto.company.companyName,
            paymentRequestDto.number,
            paymentRequestDto.amount
        )
    }

    override fun cancelPayment(externalPaymentId: String) {
        if (!paymentModule.cancelPayment(externalPaymentId)) throw FailedCancelPaymentException(externalPaymentId)
    }
}