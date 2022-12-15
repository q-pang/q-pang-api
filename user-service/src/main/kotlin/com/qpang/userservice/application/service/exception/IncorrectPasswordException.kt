package com.qpang.userservice.application.service.exception

import com.qpang.userservice.common.exception.BusinessException

class IncorrectPasswordException(username: String) : BusinessException(username) {
    override val message = "password가 일치하지 않습니다. : $username"
}