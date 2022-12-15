package com.qpang.userservice.application.service.exception

import com.qpang.userservice.common.exception.BusinessException

class UsernameNotFoundException(username: String) : BusinessException(username) {
    override val message = "username이 존재하지 않습니다. : $username"
}