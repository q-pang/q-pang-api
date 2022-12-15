package com.qpang.userservice.common.exception

open class BusinessException(override val message: String) : RuntimeException(message)