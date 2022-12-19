package com.qpang.deliveryservice.common.exception

open class BusinessException(override val message: String) : RuntimeException(message)