package com.qpang.productservice.application.service.exception

import com.qpang.productservice.common.exception.BusinessException

class ProductIdNotFoundException(id: String) : BusinessException(id) {
    override val message = "상품 id가 존재하지 않습니다. : $id"
}