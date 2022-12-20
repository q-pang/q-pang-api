package com.qpang.orderservice.application.port.out.rest

import com.qpang.orderservice.application.port.out.rest.dto.UserResponseDto

interface UserServiceRestPort {
    fun getUser(consumerId: String): UserResponseDto
}