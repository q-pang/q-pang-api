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
import com.qpang.orderservice.application.service.exception.UserNotFoundException
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
        context("?????? ????????? ????????????") {
            every { mockPaymentPort.payment(any()) } answers { "externalPaymentId" }

            context("?????? ????????? ????????? ????????????") {
                every { mockEventProducePort.order(any()) } answers {}

                context("????????? productIdList???") {

                    context("?????? ?????? ???????????? ????????? ???????????? ?????????") {
                        val expectedProductList =
                            listOf(ProductResponseDto("registeredProductId", "name", 10, 15000, ProductCategoryResponseDto("category")))
                        every { mockProductServiceRestPort.getProductListIds(allRegisteredCommand.orderItemCommands.map { it.productId }) } answers { expectedProductList }

                        it("????????? ???????????? IncorrectPriceException ??????") {
                            shouldThrow<IncorrectPriceException> {
                                registerOrderService.command(allRegisteredCommand)
                            }
                        }
                    }

                    context("?????? ?????? ???????????? ???????????? ?????? ????????? ?????????") {
                        val expectedProductList =
                            listOf(ProductResponseDto("registeredProductId", "name", 0, 16000, ProductCategoryResponseDto("category")))
                        every { mockProductServiceRestPort.getProductListIds(allRegisteredCommand.orderItemCommands.map { it.productId }) } answers { expectedProductList }

                        it("????????? ???????????? OutOfStockException ??????") {
                            shouldThrow<OutOfStockException> {
                                registerOrderService.command(allRegisteredCommand)
                            }
                        }
                    }

                    context("????????? ????????? ???????????? ?????? ???????????? ?????? ?????? ???????????? ????????? ?????????") {
                        every { mockProductServiceRestPort.getProductListIds(notRegisteredProductIdCommand.orderItemCommands.map { it.productId }) } answers { emptyList() }

                        it("????????? ???????????? ProductNotFoundException ??????") {
                            shouldThrow<ProductNotFoundException> {
                                registerOrderService.command(notRegisteredProductIdCommand)
                            }
                        }
                    }

                    context("?????? ????????? ????????? ????????????") {
                        val expectedProductList =
                            listOf(ProductResponseDto("registeredProductId", "name", 10, 16000, ProductCategoryResponseDto("category")))
                        every { mockProductServiceRestPort.getProductListIds(allRegisteredCommand.orderItemCommands.map { it.productId }) } answers { expectedProductList }

                        context("????????? consumerId??? ?????? ???????????? ????????????") {
                            every { mockUserServiceRestPort.getUser("registeredConsumerId") } answers {
                                UserResponseDto("UserResponseDto", "username", "name")
                            }
                            every { mockOrderPersistencePort.saveOrder(any()) } answers { Order(allRegisteredCommand.consumerId) }

                            it("????????? ???????????? command, info ?????? ?????????") {
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

                        context("???????????? ?????? consumerId??? ?????? ???????????? ????????????") {
                            every { mockUserServiceRestPort.getUser("notRegisteredConsumerId") }.throws(
                                UserNotFoundException("registeredConsumerId")
                            )

                            it("????????? ???????????? UserNotFoundException ??????") {
                                shouldThrow<UserNotFoundException> {
                                    registerOrderService.command(notRegisteredConsumerIdCommand)
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

        private val notRegisteredProductIdCommand = RegisterOrderUseCase.RegisterOrderCommand(
            consumerId = "registeredConsumerId",
            orderItemCommands = listOf(
                OrderItemCommand(
                    name = "name",
                    price = 16000,
                    count = 1,
                    productId = "notRegisteredProductId"
                )
            ),
            paymentCommand = PaymentCommand(
                type = Payment.PaymentMethodType.CREDITCARD,
                company = Payment.CardCompany.SAMSUNG,
                number = "1234567890",
                username = "username"
            )
        )

        private val notRegisteredConsumerIdCommand = RegisterOrderUseCase.RegisterOrderCommand(
            consumerId = "notRegisteredConsumerId",
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