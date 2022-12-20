package com.qpang.productservice.application.service

import com.qpang.productservice.application.port.`in`.usecase.GetProductListIdsUseCase
import com.qpang.productservice.application.port.out.persistence.ProductPersistencePort
import com.qpang.productservice.domain.Product
import com.qpang.productservice.domain.ProductCategory
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class GetProductListIdsServiceDescribeSpec : DescribeSpec({
    val mockProductPersistencePort: ProductPersistencePort = mockk()
    val getProductListIdsService = GetProductListIdsService(mockProductPersistencePort)

    describe("getProductListIds") {
        context("ids가 담긴 Command가 주어지면") {
            val expectedProductList = listOf(
                Product(name = "티셔츠", stock = 10, price = 100, category = ProductCategory("의류")),
                Product(name = "청바지", stock = 10, price = 100, category = ProductCategory("의류"))
            )
            every { mockProductPersistencePort.findProductsByIds(command.ids) } answers { expectedProductList }
            it("상품 리스트 조회에 성공하고 응답받은 info와 command의 ids size가 같음") {
                val resultInfoList = getProductListIdsService.command(command)

                resultInfoList.size shouldBe command.ids.size
            }
        }
    }
}) {
    companion object {
        private val command = GetProductListIdsUseCase.GetProductListIdsCommand(
            ids = listOf("id1", "id2")
        )
    }
}