package com.qpang.productservice.application.service

import com.qpang.productservice.application.port.`in`.usecase.DeleteProductCategoryUseCase
import com.qpang.productservice.application.port.out.persistence.ProductPersistencePort
import com.qpang.productservice.application.service.exception.ProductCategoryIdNotFoundException
import org.springframework.stereotype.Service

@Service
class DeleteProductCategoryService(
    private val productPersistencePort: ProductPersistencePort
) : DeleteProductCategoryUseCase {
    override fun command(command: DeleteProductCategoryUseCase.DeleteProductCategoryCommand): DeleteProductCategoryUseCase.DeleteProductCategoryInfo {
        val productCategory = productPersistencePort.findProductCategoryById(command.id)
        productCategory ?: throw ProductCategoryIdNotFoundException(command.id)
        productPersistencePort.deleteProductCategory(productCategory)

        return DeleteProductCategoryUseCase.DeleteProductCategoryInfo.from(productCategory)
    }
}