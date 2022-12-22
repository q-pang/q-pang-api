package com.qpang.orderservice.application.service

import com.qpang.orderservice.application.port.`in`.RegisterOrderUseCase
import com.qpang.orderservice.application.port.`in`.dto.OrderItemCommand
import com.qpang.orderservice.application.port.`in`.dto.PaymentCommand
import com.qpang.orderservice.application.port.out.event.EventProducePort
import com.qpang.orderservice.application.port.out.external.PaymentPort
import com.qpang.orderservice.application.port.out.persistence.OrderPersistencePort
import com.qpang.orderservice.application.port.out.rest.ProductServiceRestPort
import com.qpang.orderservice.application.port.out.rest.UserServiceRestPort
import com.qpang.orderservice.application.port.out.rest.dto.ProductCategoryResponseDto
import com.qpang.orderservice.application.port.out.rest.dto.ProductResponseDto
import com.qpang.orderservice.application.port.out.rest.dto.UserResponseDto
import com.qpang.orderservice.application.service.exception.IncorrectPriceException
import com.qpang.orderservice.application.service.exception.OutOfStockException
import com.qpang.orderservice.application.service.exception.ProductNotFoundException
import com.qpang.orderservice.domain.Order
import com.qpang.orderservice.domain.Payment
import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class RegisterOrderServiceDescribeSpec : DescribeSpec({
    val mockOrderPersistencePort: OrderPersistencePort = mockk()
    val mockProductServiceRestPort: ProductServiceRestPort = mockk()
    val mockUserServiceRestPort: UserServiceRestPort = mockk()
    val mockEventProducePort: EventProducePort = mockk()
    val mockPaymentPort: PaymentPort = mockk()
    val registerOrderService = RegisterOrderService(
        mockOrderPersistencePort,
        mockProductServiceRestPort,
        mockUserServiceRestPort,
        mockEventProducePort,
        mockPaymentPort
    )

    describe("registerOrder") {
        context("외부 결제에 성공하고") {
            every { mockPaymentPort.payment(any()) } answers { "externalPaymentId" }

            context("주문 이벤트 발행에 성공하며") {
                every { mockEventProducePort.order(any()) } answers {}

                context("등록된 productIdList가") {

                    context("상품 원본 데이터와 가격이 일치하지 않으면") {
                        val expectedProductList =
                            listOf(
                                ProductResponseDto(
                                    "registeredProductId",
                                    "name",
                                    10,
                                    15000,
                                    ProductCategoryResponseDto("category")
                                )
                            )
                        every { mockProductServiceRestPort.getProductListIds(allRegisteredCommand.orderItemCommands.map { it.productId }) } answers { expectedProductList }

                        it("주문에 실패하고 IncorrectPriceException 발생") {
                            shouldThrow<IncorrectPriceException> {
                                registerOrderService.command(allRegisteredCommand)
                            }
                        }
                    }

                    context("상품 원본 데이터의 재고보다 주문 수량이 많으면") {
                        val expectedProductList =
                            listOf(
                                ProductResponseDto(
                                    "registeredProductId",
                                    "name",
                                    0,
                                    16000,
                                    ProductCategoryResponseDto("category")
                                )
                            )
                        every { mockProductServiceRestPort.getProductListIds(allRegisteredCommand.orderItemCommands.map { it.productId }) } answers { expectedProductList }

                        it("주문에 실패하고 OutOfStockException 발생") {
                            shouldThrow<OutOfStockException> {
                                registerOrderService.command(allRegisteredCommand)
                            }
                        }
                    }

                    context("주문한 상품의 갯수보다 실제 존재하는 상품 원본 데이터의 갯수가 적으면") {
                        every { mockProductServiceRestPort.getProductListIds(allRegisteredCommand.orderItemCommands.map { it.productId }) } answers { emptyList() }

                        it("주문에 실패하고 ProductNotFoundException 발생") {
                            shouldThrow<ProductNotFoundException> {
                                registerOrderService.command(allRegisteredCommand)
                            }
                        }
                    }

                    context("상품 리스트 검증에 통과하고") {
                        val expectedProductList =
                            listOf(
                                ProductResponseDto(
                                    "registeredProductId",
                                    "name",
                                    10,
                                    16000,
                                    ProductCategoryResponseDto("category")
                                )
                            )
                        every { mockProductServiceRestPort.getProductListIds(allRegisteredCommand.orderItemCommands.map { it.productId }) } answers { expectedProductList }

                        context("등록된 consumerId를 가진 커맨드가 주어지면") {
                            every { mockUserServiceRestPort.getUser("registeredConsumerId") } answers {
                                UserResponseDto("UserResponseDto", "username", "name")
                            }
                            every { mockOrderPersistencePort.saveOrder(any()) } answers { Order(allRegisteredCommand.consumerId) }

                            it("주문에 성공하고 command, info 값이 일치함") {
                                val resultInfo = registerOrderService.command(allRegisteredCommand)

                                assertSoftly {
                                    resultInfo.consumerId shouldBe allRegisteredCommand.consumerId
                                    resultInfo.orderItemInfos[0].name shouldBe allRegisteredCommand.orderItemCommands[0].name
                                    resultInfo.orderItemInfos[0].price shouldBe allRegisteredCommand.orderItemCommands[0].price
                                    resultInfo.orderItemInfos[0].count shouldBe allRegisteredCommand.orderItemCommands[0].count
                                    resultInfo.orderItemInfos[0].productId shouldBe allRegisteredCommand.orderItemCommands[0].productId
                                    resultInfo.paymentInfo.username shouldBe allRegisteredCommand.paymentCommand.username
                                    resultInfo.paymentInfo.type shouldBe allRegisteredCommand.paymentCommand.type
                                    resultInfo.paymentInfo.company shouldBe allRegisteredCommand.paymentCommand.company
                                    resultInfo.paymentInfo.number shouldBe allRegisteredCommand.paymentCommand.number
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}) {
    companion object {
        private val allRegisteredCommand = RegisterOrderUseCase.RegisterOrderCommand(
            consumerId = "registeredConsumerId",
            orderItemCommands = listOf(
                OrderItemCommand(
                    name = "name",
                    price = 16000,
                    count = 1,
                    productId = "registeredProductId"
                )
            ),
            paymentCommand = PaymentCommand(
                type = Payment.PaymentMethodType.CREDITCARD,
                company = Payment.CardCompany.SAMSUNG,
                number = "1234567890",
                username = "username"
            )
        )
    }
}