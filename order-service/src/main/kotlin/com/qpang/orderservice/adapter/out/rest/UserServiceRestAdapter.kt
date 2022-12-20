package com.qpang.orderservice.adapter.out.rest

import com.qpang.orderservice.application.port.out.rest.UserServiceRestPort
import com.qpang.orderservice.application.port.out.rest.dto.UserResponseDto
import com.qpang.orderservice.infrastructure.feignclient.UserServiceClient
import org.springframework.stereotype.Component

@Component
class UserServiceRestAdapter(
    private val userServiceClient: UserServiceClient
) : UserServiceRestPort {
    override fun getUser(consumerId: String): UserResponseDto = userServiceClient.getUser(consumerId)
}