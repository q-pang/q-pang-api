package com.qpang.productservice.application.service

import com.qpang.productservice.application.port.`in`.usecase.RegisterProductCategoryUseCase
import com.qpang.productservice.application.port.out.persistence.ProductPersistencePort
import com.qpang.productservice.application.service.exception.DuplicateProductCategoryNameException
import org.springframework.stereotype.Service

@Service
class RegisterProductCategoryService(
    private val productPersistencePort: ProductPersistencePort
) : RegisterProductCategoryUseCase {
    override fun command(command: RegisterProductCategoryUseCase.RegisterProductCategoryCommand): RegisterProductCategoryUseCase.RegisterProductCategoryInfo {
        if (productPersistencePort.existsProductCategoryByName(command.name)) {
            throw DuplicateProductCategoryNameException(command.name)
        }

        return RegisterProductCategoryUseCase.RegisterProductCategoryInfo.from(productPersistencePort.saveProductCategory(command.toEntity()))
    }
}