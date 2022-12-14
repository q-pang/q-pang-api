package com.qpang.productservice.application.service

import com.qpang.productservice.application.port.`in`.usecase.RegisterProductCategoryUseCase
import com.qpang.productservice.application.port.out.persistence.ProductPersistencePort
import com.qpang.productservice.application.service.exception.DuplicateProductCategoryNameException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RegisterProductCategoryService(
    private val productPersistencePort: ProductPersistencePort
) : RegisterProductCategoryUseCase {
    @Transactional
    override fun command(command: RegisterProductCategoryUseCase.RegisterProductCategoryCommand): RegisterProductCategoryUseCase.RegisterProductCategoryInfo {
        if (productPersistencePort.existsProductCategoryByName(command.name)) {
            throw DuplicateProductCategoryNameException(command.name)
        }
        val savedProductCategory = productPersistencePort.saveProductCategory(command.toEntity())

        return RegisterProductCategoryUseCase.RegisterProductCategoryInfo.from(savedProductCategory)
    }
}