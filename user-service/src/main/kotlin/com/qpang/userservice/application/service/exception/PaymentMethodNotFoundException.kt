package com.qpang.userservice.application.service.exception

import com.qpang.userservice.common.exception.BusinessException

class PaymentMethodNotFoundException(paymentMethodId: String) : BusinessException(paymentMethodId) {
    override val message = "결제수단이 존재하지 않습니다. : $paymentMethodId"
}