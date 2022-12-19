package com.qpang.productservice.application.service

import com.qpang.productservice.application.port.`in`.usecase.RegisterProductUseCase
import com.qpang.productservice.application.port.out.persistence.ProductPersistencePort
import com.qpang.productservice.application.service.exception.ProductCategoryIdNotFoundException
import com.qpang.productservice.domain.Product
import com.qpang.productservice.domain.ProductCategory
import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class RegisterProductServiceDescribeSpec : DescribeSpec({
    val mockProductPersistencePort: ProductPersistencePort = mockk()
    val registerProductService = RegisterProductService(mockProductPersistencePort)

    describe("registerProduct") {
        context("등록된 categoryId을 가진 Command가 주어지면") {
            val expectedProductCategory = ProductCategory("의류")
            val expectedProduct = Product(
                name = registeredCategoryIdCommand.name,
                registeredCategoryIdCommand.stock,
                price = registeredCategoryIdCommand.price,
                category = expectedProductCategory
            )
            every { mockProductPersistencePort.findProductCategoryById(registeredCategoryIdCommand.categoryId) } answers { expectedProductCategory }
            every { mockProductPersistencePort.saveProduct(any()) } answers { expectedProduct }
            every { mockProductPersistencePort.findProductById(any()) } answers { expectedProduct }
            it("상품 등록에 성공하고 RegisterProductInfo 응답") {
                val resultInfo = registerProductService.command(registeredCategoryIdCommand)

                assertSoftly {
                    resultInfo.name shouldBe registeredCategoryIdCommand.name
                    resultInfo.stock shouldBe registeredCategoryIdCommand.stock
                }
            }
        }

        context("등록되지 않은 categoryId을 가진 Command가 주어지면") {
            every { mockProductPersistencePort.findProductCategoryById(notRegisteredCategoryIdCommand.categoryId) } answers { null }
            it("상품 등록에 실패하고 ProductCategoryIdNotFoundException 발생") {
                shouldThrow<ProductCategoryIdNotFoundException> {
                    registerProductService.command(notRegisteredCategoryIdCommand)
                }
            }
        }
    }
}) {
    companion object {
        private val registeredCategoryIdCommand = RegisterProductUseCase.RegisterProductCommand(
            name = "name",
            stock = 10,
            price = 100,
            categoryId = "registeredCategoryId"
        )

        private val notRegisteredCategoryIdCommand = RegisterProductUseCase.RegisterProductCommand(
            name = "name",
            stock = 10,
            price = 100,
            categoryId = "notRegisteredCategoryId"
        )
    }
}