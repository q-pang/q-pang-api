package com.qpang.productservice.application.port.`in`.usecase.info

import com.qpang.productservice.domain.ProductCategory

data class ProductCategoryInfo(
    val id: String,
    val name: String
) {
    companion object {
        fun from(productCategory: ProductCategory): ProductCategoryInfo = ProductCategoryInfo(
            id = productCategory.getId(),
            name = productCategory.name
        )
    }
}
