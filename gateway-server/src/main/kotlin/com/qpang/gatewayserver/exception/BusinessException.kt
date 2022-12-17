package com.qpang.gatewayserver.exception

open class BusinessException(override val message: String) : RuntimeException(message)