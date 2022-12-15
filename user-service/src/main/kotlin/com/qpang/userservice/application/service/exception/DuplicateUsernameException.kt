package com.qpang.userservice.application.service.exception

import com.qpang.userservice.common.exception.BusinessException

class DuplicateUsernameException(username: String) : BusinessException(username) {
    override val message = "username이 이미 존재합니다. : $username"
}