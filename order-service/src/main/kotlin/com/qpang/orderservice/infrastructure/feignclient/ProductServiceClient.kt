package com.qpang.orderservice.infrastructure.feignclient

import com.qpang.orderservice.application.port.out.rest.dto.ProductResponseDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "product-service")
interface ProductServiceClient {
    @GetMapping("/product/list/ids")
    fun getProductListIds(@RequestParam productIds: List<String>): List<ProductResponseDto>
}