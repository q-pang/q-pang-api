package com.qpang.productservice.adapter.`in`.rest

import com.qpang.productservice.adapter.`in`.rest.dto.ProductCategoryResponseDto
import com.qpang.productservice.application.port.`in`.usecase.GetProductListIdsUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class GetProductListIdsRestAdapter(
    private val getProductListIdsUseCase: GetProductListIdsUseCase
) {
    @GetMapping("/product/list/ids")
    fun getProductListIds(@RequestParam productIds: List<String>): ResponseEntity<List<GetProductListIdsResponseDto>> =
        ResponseEntity.ok().body(
            GetProductListIdsResponseDto.from(
                getProductListIdsUseCase.command(
                    GetProductListIdsUseCase.GetProductListIdsCommand(productIds)
                )
            )
        )

    data class GetProductListIdsResponseDto(
        val id: String,
        val name: String,
        val stock: Long,
        val price: Long,
        val category: ProductCategoryResponseDto
    ) {
        companion object {
            fun from(infoList: List<GetProductListIdsUseCase.GetProductListIdsInfo>): List<GetProductListIdsResponseDto> =
                infoList.map {
                    GetProductListIdsResponseDto(
                        id = it.id,
                        name = it.name,
                        stock = it.stock,
                        price = it.price,
                        category = ProductCategoryResponseDto.from(it.category)
                    )
                }
        }
    }
}