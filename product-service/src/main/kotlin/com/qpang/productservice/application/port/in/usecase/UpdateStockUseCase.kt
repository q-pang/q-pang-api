package com.qpang.productservice.application.port.`in`.usecase

import com.qpang.productservice.application.port.`in`.usecase.info.ProductCategoryInfo
import com.qpang.productservice.domain.Product

interface UpdateStockUseCase {
    fun command(command: UpdateStockCommand): UpdateStockInfo

    data class UpdateStockCommand(
        val id: String,
        val updatedStock: Long
    )

    data class UpdateStockInfo(
        val id: String,
        val name: String,
        val stock: Long,
        val price: Long,
        val category: ProductCategoryInfo
    ) {
        companion object {
            fun from(product: Product) = UpdateStockInfo(
                id = product.getId(),
                name = product.name,
                stock = product.stock,
                price = product.stock,
                category = ProductCategoryInfo.from(product.category)
            )
        }
    }
}