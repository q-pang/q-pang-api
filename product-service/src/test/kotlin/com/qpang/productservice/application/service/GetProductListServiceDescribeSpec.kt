package com.qpang.productservice.application.service

import com.qpang.productservice.application.port.`in`.usecase.GetProductListUseCase
import com.qpang.productservice.application.port.out.persistence.ProductPersistencePort
import com.qpang.productservice.domain.Product
import com.qpang.productservice.domain.ProductCategory
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk

class GetProductListServiceDescribeSpec : DescribeSpec({
    val mockProductPersistencePort: ProductPersistencePort = mockk()
    val getProductListService = GetProductListService(mockProductPersistencePort)

    describe("getProductList") {
        context("name이 존재하는 Command가 주어지면") {
            val expectedProductList = listOf(
                Product(name = "티셔츠", stock = 10, price = 100, category = ProductCategory("의류")),
                Product(name = "청바지", stock = 10, price = 100, category = ProductCategory("의류"))
            )
            every { mockProductPersistencePort.findAllProduct(nameCommand.categoryId, nameCommand.name) } answers { expectedProductList }
            it("상품 리스트 조회에 성공하고 List<GetProductListInfo> 응답") {
                val resultInfoList = getProductListService.command(nameCommand)
            }
        }

        context("categoryId가 존재하는 Command가 주어지면") {
            val expectedProductList = listOf(
                Product(name = "티셔츠", stock = 10, price = 100, category = ProductCategory("의류")),
                Product(name = "청바지", stock = 10, price = 100, category = ProductCategory("의류"))
            )
            every { mockProductPersistencePort.findAllProduct(categoryIdCommand.categoryId, categoryIdCommand.name) } answers { expectedProductList }
            it("상품 리스트 조회에 성공하고 List<GetProductListInfo> 응답") {
                val resultInfoList = getProductListService.command(categoryIdCommand)
            }
        }
    }
}) {
    companion object {
        private val nameCommand = GetProductListUseCase.GetProductListCommand(
            categoryId = null,
            name = "티셔츠"
        )
        private val categoryIdCommand = GetProductListUseCase.GetProductListCommand(
            categoryId = "categoryId",
            name = null
        )
    }
}