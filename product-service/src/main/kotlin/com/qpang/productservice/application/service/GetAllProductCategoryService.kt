package com.qpang.productservice.application.service

import com.qpang.productservice.application.port.`in`.usecase.GetAllProductCategoryUseCase
import com.qpang.productservice.application.port.out.persistence.ProductPersistencePort
import org.springframework.stereotype.Service

@Service
class GetAllProductCategoryService(
    private val productCategoryPersistencePort: ProductPersistencePort
) : GetAllProductCategoryUseCase {
    override fun command(): List<GetAllProductCategoryUseCase.GetProductCategoryInfo> {
        val productCategoryList = productCategoryPersistencePort.findAllProductCategory()
        return productCategoryList.map { GetAllProductCategoryUseCase.GetProductCategoryInfo.from(it) }
    }
}