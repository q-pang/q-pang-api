package com.qpang.orderservice.application.service.exception

import com.qpang.orderservice.common.exception.BusinessException

class ProductNotFoundException(id: String, name: String) : BusinessException(id) {
    override val message = "상품[$name]이 존재하지 않습니다. : $id"
}