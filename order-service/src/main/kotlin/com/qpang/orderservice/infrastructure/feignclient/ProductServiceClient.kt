package com.qpang.orderservice.infrastructure.feignclient

import com.qpang.orderservice.application.port.out.rest.dto.ProductResponseDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.cloud.openfeign.SpringQueryMap
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(name = "product-service")
interface ProductServiceClient {
    @GetMapping("/product/list/ids")
    fun getProductListIds(@SpringQueryMap productIds: List<String>): List<ProductResponseDto>
}