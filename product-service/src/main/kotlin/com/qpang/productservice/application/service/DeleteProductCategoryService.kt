package com.qpang.productservice.application.service

import com.qpang.productservice.application.port.`in`.usecase.DeleteProductCategoryUseCase
import com.qpang.productservice.application.port.out.persistence.ProductPersistencePort
import com.qpang.productservice.application.service.exception.ProductCategoryIdNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DeleteProductCategoryService(
    private val productPersistencePort: ProductPersistencePort
) : DeleteProductCategoryUseCase {
    @Transactional
    override fun command(command: DeleteProductCategoryUseCase.DeleteProductCategoryCommand): DeleteProductCategoryUseCase.DeleteProductCategoryInfo {
        val savedProductCategory = productPersistencePort.findProductCategoryById(command.id)
        savedProductCategory ?: throw ProductCategoryIdNotFoundException(command.id)
        productPersistencePort.deleteProductCategory(savedProductCategory)

        return DeleteProductCategoryUseCase.DeleteProductCategoryInfo.from(savedProductCategory)
    }
}