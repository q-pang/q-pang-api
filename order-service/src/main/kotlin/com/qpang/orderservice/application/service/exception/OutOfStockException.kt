package com.qpang.orderservice.application.service.exception

import com.qpang.orderservice.common.exception.BusinessException

class OutOfStockException(id: String, name: String, stock: Long) : BusinessException(id) {
    override val message = "상품[$name]의 재고[$stock]가 부족합니다. : $id"
}