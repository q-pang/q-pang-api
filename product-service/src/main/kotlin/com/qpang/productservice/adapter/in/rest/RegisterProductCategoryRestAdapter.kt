package com.qpang.productservice.adapter.`in`.rest

import com.qpang.productservice.application.port.`in`.usecase.RegisterProductCategoryUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class RegisterProductCategoryRestAdapter(
    private val registerProductCategoryUseCase: RegisterProductCategoryUseCase
) {
    @PostMapping("/product/category")
    fun registerProductCategory(@RequestBody @Valid dto: RegisterProductCategoryRequestDto): ResponseEntity<RegisterProductCategoryResponseDto> =
        ResponseEntity(
            RegisterProductCategoryResponseDto.from(registerProductCategoryUseCase.command(dto.toCommand())),
            HttpStatus.CREATED
        )

    data class RegisterProductCategoryRequestDto(
        val name: String
    ) {
        fun toCommand(): RegisterProductCategoryUseCase.RegisterProductCategoryCommand =
            RegisterProductCategoryUseCase.RegisterProductCategoryCommand(name = name)
    }

    data class RegisterProductCategoryResponseDto(
        val id: String,
        val name: String
    ) {
        companion object {
            fun from(info: RegisterProductCategoryUseCase.RegisterProductCategoryInfo): RegisterProductCategoryResponseDto =
                RegisterProductCategoryResponseDto(
                    id = info.id,
                    name = info.name
                )
        }
    }
}