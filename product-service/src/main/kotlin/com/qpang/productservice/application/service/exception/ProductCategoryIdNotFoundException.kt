package com.qpang.productservice.application.service.exception

import com.qpang.productservice.common.exception.BusinessException

class ProductCategoryIdNotFoundException(id: String) : BusinessException(id) {
    override val message = "상품 카테고리 id가 존재하지 않습니다. : $id"
}