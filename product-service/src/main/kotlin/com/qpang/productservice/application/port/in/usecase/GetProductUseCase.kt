package com.qpang.productservice.application.port.`in`.usecase

import com.qpang.productservice.application.port.`in`.usecase.info.ProductCategoryInfo
import com.qpang.productservice.domain.Product

interface GetProductUseCase {
    fun command(command: GetProductCommand): GetProductInfo

    data class GetProductCommand(
        val id: String
    )

    data class GetProductInfo(
        val id: String,
        val name: String,
        val stock: Long,
        val price: Long,
        val category: ProductCategoryInfo
    ) {
        companion object {
            fun from(product: Product) = GetProductInfo(
                id = product.getId(),
                name = product.name,
                stock = product.stock,
                price = product.price,
                category = ProductCategoryInfo.from(product.category)
            )
        }
    }
}