package com.qpang.productservice.adapter.`in`.rest

import com.qpang.productservice.adapter.`in`.rest.dto.ProductCategoryResponseDto
import com.qpang.productservice.application.port.`in`.usecase.GetProductListUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class GetProductListRestAdapter(
    private val getProductListUseCase: GetProductListUseCase
) {
    @GetMapping("/product/list")
    fun getProductList(@RequestBody @Valid dto: GetProductListRequestDto): ResponseEntity<List<GetProductListResponseDto>> =
        ResponseEntity.ok().body(GetProductListResponseDto.from(getProductListUseCase.command(dto.toCommand())))

    data class GetProductListRequestDto(
        val categoryId: String?
    ) {
        fun toCommand(): GetProductListUseCase.GetProductListCommand = GetProductListUseCase.GetProductListCommand(
            categoryId = categoryId
        )
    }

    data class GetProductListResponseDto(
        val id: String,
        val name: String,
        val stock: Long,
        val price: Long,
        val category: ProductCategoryResponseDto
    ) {
        companion object {
            fun from(getProductListInfoList: List<GetProductListUseCase.GetProductListInfo>): List<GetProductListResponseDto> =
                getProductListInfoList.map {
                    GetProductListResponseDto(
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