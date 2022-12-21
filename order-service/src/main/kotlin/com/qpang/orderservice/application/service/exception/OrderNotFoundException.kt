package com.qpang.orderservice.application.service.exception

import com.qpang.orderservice.common.exception.BusinessException

class OrderNotFoundException(id: String) : BusinessException(id) {
    override val message = "주문이 존재하지 않습니다. : $id"
}