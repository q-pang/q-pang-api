package com.qpang.orderservice.adapter.out.external.exception

import com.qpang.orderservice.common.exception.BusinessException

class FailedCancelPaymentException(id: String) : BusinessException(id) {
    override val message = "결제 취소에 실패하였습니다. : $id"
}