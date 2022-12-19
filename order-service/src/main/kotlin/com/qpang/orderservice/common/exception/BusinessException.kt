package com.qpang.orderservice.common.exception

open class BusinessException(override val message: String) : RuntimeException(message)