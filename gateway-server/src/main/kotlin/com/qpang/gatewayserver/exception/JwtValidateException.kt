package com.qpang.gatewayserver.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "invalid token")
class JwtValidateException(token: String) : BusinessException(token) {
    override val message = "token 검증에 실패하였습니다. : $token"
}