package com.qpang.productservice.application.service

import com.qpang.productservice.application.port.`in`.usecase.GetAllProductCategoryUseCase
import com.qpang.productservice.application.port.out.persistence.ProductPersistencePort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetAllProductCategoryService(
    private val productCategoryPersistencePort: ProductPersistencePort
) : GetAllProductCategoryUseCase {
    @Transactional(readOnly = true)
    override fun command(): List<GetAllProductCategoryUseCase.GetProductCategoryInfo> {
        val savedProductCategoryList = productCategoryPersistencePort.findAllProductCategory()

        return GetAllProductCategoryUseCase.GetProductCategoryInfo.from(savedProductCategoryList)
    }
}