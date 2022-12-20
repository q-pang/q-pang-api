package com.qpang.orderservice.application.service.exception

import com.qpang.orderservice.common.exception.BusinessException

class IncorrectPriceException(id: String, name: String, price: Long) : BusinessException(id) {
    override val message = "상품[$name]의 가격[$price]이 일치하지 않습니다. : $id"
}