package com.qpang.orderservice.infrastructure.feignclient

import com.qpang.orderservice.application.port.out.rest.dto.ProductResponseDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient("products")
interface ProductServiceClient {
    @GetMapping("/product/list/ids")
    fun getProductListIds(productIds: List<String>): List<ProductResponseDto>
}