package com.qpang.orderservice.infrastructure.feignclient

import com.qpang.orderservice.application.port.out.rest.dto.UserResponseDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "user-service")
interface UserServiceClient {
    @GetMapping("/user/{id}")
    fun getUser(@PathVariable consumerId: String): UserResponseDto
}