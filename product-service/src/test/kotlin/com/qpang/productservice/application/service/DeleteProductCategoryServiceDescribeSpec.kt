package com.qpang.productservice.application.service

import com.qpang.productservice.application.port.`in`.usecase.DeleteProductCategoryUseCase
import com.qpang.productservice.application.port.out.persistence.ProductPersistencePort
import com.qpang.productservice.application.service.exception.ProductCategoryIdNotFoundException
import com.qpang.productservice.domain.ProductCategory
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk

class DeleteProductCategoryServiceDescribeSpec : DescribeSpec({
    val mockProductPersistencePort: ProductPersistencePort = mockk()
    val deleteProductCategoryService = DeleteProductCategoryService(mockProductPersistencePort)

    describe("deleteProductCategory") {
        context("등록된 id를 가진 Command가 주어지면") {
            val expectedProductCategory = ProductCategory(name = "name")
            every { mockProductPersistencePort.findProductCategoryById(registeredIdCommand.id) } answers { expectedProductCategory }
            every { mockProductPersistencePort.deleteProductCategory(expectedProductCategory) } answers {}
            it("상품 카테고리 삭제에 성공하고 DeleteProductCategoryInfo 응답") {
                deleteProductCategoryService.command(registeredIdCommand)
            }
        }

        context("등록되지 않은 id를 가진 Command가 주어지면") {
            every { mockProductPersistencePort.findProductCategoryById(notRegisteredIdCommand.id) } answers { null }
            it("상품 카테고리 등록에 실패하고 ProductCategoryIdNotFoundException 발생") {
                shouldThrow<ProductCategoryIdNotFoundException> {
                    deleteProductCategoryService.command(notRegisteredIdCommand)
                }
            }
        }
    }
}) {
    companion object {
        private val registeredIdCommand = DeleteProductCategoryUseCase.DeleteProductCategoryCommand(
            id = "id"
        )

        private val notRegisteredIdCommand = DeleteProductCategoryUseCase.DeleteProductCategoryCommand(
            id = "id"
        )
    }
}