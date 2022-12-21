package com.qpang.orderservice.domain.exception

import com.qpang.orderservice.common.exception.BusinessException

class AlreadyCanceledFoundException(id: String) : BusinessException(id) {
    override val message = "이미 취소된 주문입니다. : $id"
}