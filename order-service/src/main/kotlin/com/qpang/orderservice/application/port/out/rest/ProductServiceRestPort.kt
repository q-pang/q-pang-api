package com.qpang.orderservice.application.port.out.rest

import com.qpang.orderservice.application.port.out.rest.dto.ProductResponseDto

interface ProductServiceRestPort {
    fun getProductListIds(productIds: List<String>): List<ProductResponseDto>
}