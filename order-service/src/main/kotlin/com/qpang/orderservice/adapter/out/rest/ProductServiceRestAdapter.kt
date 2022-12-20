package com.qpang.orderservice.adapter.out.rest

import com.qpang.orderservice.application.port.out.rest.ProductServiceRestPort
import com.qpang.orderservice.application.port.out.rest.dto.ProductResponseDto
import com.qpang.orderservice.infrastructure.feignclient.ProductServiceClient
import org.springframework.stereotype.Component

@Component
class ProductServiceRestAdapter(
    private val productServiceClient: ProductServiceClient
) : ProductServiceRestPort {
    override fun getProductListIds(productIds: List<String>): List<ProductResponseDto> =
        productServiceClient.getProductListIds(productIds)
}