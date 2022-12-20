package com.qpang.productservice.application.port.`in`.usecase

import com.qpang.productservice.application.port.`in`.usecase.info.ProductCategoryInfo
import com.qpang.productservice.domain.Product

interface GetProductListIdsUseCase {
    fun command(command: GetProductListIdsCommand): List<GetProductListIdsInfo>
    data class GetProductListIdsCommand(
        val ids: List<String>
    )

    data class GetProductListIdsInfo(
        val id: String,
        val name: String,
        val stock: Long,
        val price: Long,
        val category: ProductCategoryInfo
    ) {
        companion object {
            fun from(productList: List<Product>): List<GetProductListIdsInfo> = productList.map {
                GetProductListIdsInfo(
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