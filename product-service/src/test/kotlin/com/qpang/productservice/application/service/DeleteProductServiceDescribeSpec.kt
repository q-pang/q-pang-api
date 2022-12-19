package com.qpang.productservice.application.service

import com.qpang.productservice.application.port.`in`.usecase.DeleteProductUseCase
import com.qpang.productservice.application.port.out.persistence.ProductPersistencePort
import com.qpang.productservice.application.service.exception.ProductIdNotFoundException
import com.qpang.productservice.domain.Product
import com.qpang.productservice.domain.ProductCategory
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk

class DeleteProductServiceDescribeSpec : DescribeSpec({
    val mockProductPersistencePort: ProductPersistencePort = mockk()
    val deleteProductService = DeleteProductService(mockProductPersistencePort)

    describe("deleteProduct") {
        context("등록된 id를 가진 Command가 주어지면") {
            val expectedProduct = Product(name = "name", stock = 1, category = ProductCategory("name"))
            every { mockProductPersistencePort.findProductById(registeredIdCommand.id) } answers { expectedProduct }
            every { mockProductPersistencePort.deleteProduct(expectedProduct) } answers {}
            it("상품 삭제에 성공하고 DeleteProductInfo 응답") {
                deleteProductService.command(registeredIdCommand)
            }
        }

        context("등록되지 않은 id를 가진 Command가 주어지면") {
            every { mockProductPersistencePort.findProductById(notRegisteredIdCommand.id) } answers { null }
            it("상품 등록에 실패하고 ProductIdNotFoundException 발생") {
                shouldThrow<ProductIdNotFoundException> {
                    deleteProductService.command(notRegisteredIdCommand)
                }
            }
        }
    }
}) {
    companion object {
        private val registeredIdCommand = DeleteProductUseCase.DeleteProductCommand(
            id = "id"
        )

        private val notRegisteredIdCommand = DeleteProductUseCase.DeleteProductCommand(
            id = "id"
        )
    }
}