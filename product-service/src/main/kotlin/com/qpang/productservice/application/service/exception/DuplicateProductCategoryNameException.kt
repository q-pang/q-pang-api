package com.qpang.productservice.application.service.exception

import com.qpang.productservice.common.exception.BusinessException

class DuplicateProductCategoryNameException(name: String) : BusinessException(name) {
    override val message = "상품 카테고리 name이 이미 존재합니다. : $name"
}