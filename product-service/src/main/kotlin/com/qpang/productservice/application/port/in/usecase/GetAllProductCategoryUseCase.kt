package com.qpang.productservice.application.port.`in`.usecase

import com.qpang.productservice.domain.ProductCategory

interface GetAllProductCategoryUseCase {
    fun command(): List<GetProductCategoryInfo>

    data class GetProductCategoryInfo(
        val id: String,
        val name: String
    ) {
        companion object {
            fun from(productCategoryList: List<ProductCategory>): List<GetProductCategoryInfo> =
                productCategoryList.map {
                    GetProductCategoryInfo(
                        id = it.getId(),
                        name = it.name
                    )
                }
        }
    }
}