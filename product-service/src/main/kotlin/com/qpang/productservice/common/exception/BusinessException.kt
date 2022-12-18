package com.qpang.productservice.common.exception

open class BusinessException(override val message: String) : RuntimeException(message)