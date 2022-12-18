package com.qpang.productservice.adapter.`in`.rest

import com.qpang.productservice.application.port.`in`.usecase.GetAllProductCategoryUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GetAllProductCategoryRestAdapter(
    private val getAllProductCategoryUseCase: GetAllProductCategoryUseCase
) {
    @GetMapping("/product/category/all")
    fun getAllProductCategory(): ResponseEntity<List<GetProductCategoryResponseDto>> =
        ResponseEntity.ok().body(getAllProductCategoryUseCase.command().map { GetProductCategoryResponseDto.from(it) })

    data class GetProductCategoryResponseDto(
        val id: String,
        val name: String
    ) {
        companion object {
            fun from(info: GetAllProductCategoryUseCase.GetProductCategoryInfo) = GetProductCategoryResponseDto(
                id = info.id,
                name = info.name
            )
        }
    }
}