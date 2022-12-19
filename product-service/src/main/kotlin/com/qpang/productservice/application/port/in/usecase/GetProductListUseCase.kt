package com.qpang.productservice.application.port.`in`.usecase

import com.qpang.productservice.application.port.`in`.usecase.info.ProductCategoryInfo
import com.qpang.productservice.domain.Product

interface GetProductListUseCase {
    fun command(command: GetProductListCommand): List<GetProductListInfo>

    data class GetProductListCommand(
        val categoryId: String?
    )

    data class GetProductListInfo(
        val id: String,
        val name: String,
        val stock: Long,
        val price: Long,
        val category: ProductCategoryInfo
    ) {
        companion object {
            fun from(productList: List<Product>): List<GetProductListInfo> = productList.map {
                GetProductListInfo(
                    id = it.getId(),
                    name = it.name,
                    stock = it.stock,
                    price = it.price,
                    category = ProductCategoryInfo.from(it.category)
                )
            }
        }
    }
}