package com.qpang.userservice.application.service.exception

import com.qpang.userservice.common.exception.BusinessException

class UserIdNotFoundException(userId: String) : BusinessException(userId) {
    override val message = "User ID가 존재하지 않습니다. : $userId"
}