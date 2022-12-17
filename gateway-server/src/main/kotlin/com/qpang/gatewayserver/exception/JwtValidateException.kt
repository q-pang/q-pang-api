package com.qpang.gatewayserver.exception

class JwtValidateException(token: String) : BusinessException(token) {
    override val message = "token 검증에 실패하였습니다. : $token"
}