package com.qpang.orderservice.infrastructure.feignclient

import com.qpang.orderservice.application.port.out.rest.dto.DeliveryResponseDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "delivery-service")
interface DeliveryServiceClient {
    @GetMapping("/delivery/{id}")
    fun getUser(@PathVariable id: String): DeliveryResponseDto
}