package com.qpang.orderservice.application.service.exception

import com.qpang.orderservice.common.exception.BusinessException

class UserNotFoundException(id: String) : BusinessException(id) {
    override val message = "회원이 존재하지 않습니다. : $id"
}