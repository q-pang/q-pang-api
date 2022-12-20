package com.qpang.userservice.application.service.exception

import com.qpang.userservice.common.exception.BusinessException

class UserIdNotFoundException(id: String) : BusinessException(id) {
    override val message = "userId가 존재하지 않습니다. : $id"
}