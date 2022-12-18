package com.qpang.productservice.application.port.`in`.usecase

import com.qpang.productservice.domain.ProductCategory

interface GetAllProductCategoryUseCase {
    fun command(): List<GetProductCategoryInfo>

    data class GetProductCategoryInfo(
        val id: String,
        val name: String
    ) {
        companion object {
            fun from(productCategory: ProductCategory) = GetProductCategoryInfo(
                id = productCategory.getId(),
                name = productCategory.name
            )
        }
    }
}