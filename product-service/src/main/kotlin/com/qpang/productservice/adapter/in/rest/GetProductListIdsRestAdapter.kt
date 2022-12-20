package com.qpang.productservice.adapter.`in`.rest

import com.qpang.productservice.adapter.`in`.rest.dto.ProductCategoryResponseDto
import com.qpang.productservice.application.port.`in`.usecase.GetProductListIdsUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class GetProductListIdsRestAdapter(
    private val getProductListIdsUseCase: GetProductListIdsUseCase
) {
    @GetMapping("/product/list/ids")
    fun getProductListIds(@RequestBody @Valid dto: GetProductListIdsRequestDto): ResponseEntity<List<GetProductListIdsResponseDto>> =
        ResponseEntity.ok().body(GetProductListIdsResponseDto.from(getProductListIdsUseCase.command(dto.toCommand())))

    data class GetProductListIdsRequestDto(
        val ids: List<String>
    ) {
        fun toCommand(): GetProductListIdsUseCase.GetProductListIdsCommand =
            GetProductListIdsUseCase.GetProductListIdsCommand(ids = ids)
    }

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