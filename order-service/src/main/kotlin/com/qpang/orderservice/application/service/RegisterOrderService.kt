package com.qpang.orderservice.application.service

import com.qpang.orderservice.application.port.`in`.RegisterOrderUseCase
import com.qpang.orderservice.application.port.`in`.dto.OrderItemCommand
import com.qpang.orderservice.application.port.out.persistence.OrderPersistencePort
import com.qpang.orderservice.application.port.out.rest.ProductServiceRestPort
import com.qpang.orderservice.application.port.out.rest.UserServiceRestPort
import com.qpang.orderservice.application.port.out.rest.dto.ProductResponseDto
import com.qpang.orderservice.application.port.out.rest.dto.UserResponseDto
import com.qpang.orderservice.application.service.exception.IncorrectPriceException
import com.qpang.orderservice.application.service.exception.OutOfStockException
import com.qpang.orderservice.application.service.exception.ProductNotFoundException
import com.qpang.orderservice.application.service.exception.UserNotFoundException
import com.qpang.orderservice.domain.Order
import com.qpang.orderservice.domain.OrderItem
import com.qpang.orderservice.domain.Payment
import feign.FeignException.FeignServerException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RegisterOrderService(
    private val orderPersistencePort: OrderPersistencePort,
    private val productServiceRestPort: ProductServiceRestPort,
    private val userServiceRestPort: UserServiceRestPort
) : RegisterOrderUseCase {
    @Transactional
    override fun command(command: RegisterOrderUseCase.RegisterOrderCommand): RegisterOrderUseCase.RegisterOrderInfo {
        val savedProductList = getProductEntities(command.orderItemCommands)
        verifyProducts(command.orderItemCommands, savedProductList)
        val savedUser = getUserEntity(command.consumerId)

        val savedOrder = orderPersistencePort.saveOrder(Order(command.consumerId))
        val newOrderItems = createNewOrderItems(command, savedOrder)
        val newPayment = createNewPayment(command, savedOrder)
        val totalPrice = calculateTotalCount(newOrderItems)
        savedOrder.addOrderItems(newOrderItems)
        savedOrder.addPayment(newPayment)
        savedOrder.addTotalPrice(totalPrice)

        return RegisterOrderUseCase.RegisterOrderInfo.from(savedOrder)
    }

    private fun getUserEntity(consumerId: String): UserResponseDto {
        try {
            return userServiceRestPort.getUser(consumerId)
        } catch (e: FeignServerException) {
            throw UserNotFoundException(consumerId)
        }
    }

    private fun getProductEntities(orderItemCommandList: List<OrderItemCommand>): List<ProductResponseDto> {
        var productIds = mutableListOf<String>()
        orderItemCommandList.forEach { productIds.add(it.productId) }

        return productServiceRestPort.getProductListIds(productIds)
    }

    private fun verifyProducts(
        orderItemCommandList: List<OrderItemCommand>,
        savedProductList: List<ProductResponseDto>
    ) {
        var productCount = 0
        for (i in orderItemCommandList.indices) {
            savedProductList.forEach {
                if (it.id == orderItemCommandList[i].productId) {
                    productCount++
                    if (it.stock < orderItemCommandList[i].count) {
                        throw OutOfStockException(it.id, it.name, it.stock)
                    }
                    if (it.price != orderItemCommandList[i].price) {
                        throw IncorrectPriceException(it.id, it.name, it.price)
                    }
                }
            }
            if (i + 1 > productCount) {
                throw ProductNotFoundException(orderItemCommandList[i].productId, orderItemCommandList[i].name)
            }
        }
    }

    private fun createNewOrderItems(
        command: RegisterOrderUseCase.RegisterOrderCommand,
        savedOrder: Order
    ) = command.orderItemCommands.map {
        OrderItem(
            name = it.name,
            price = it.price,
            count = it.count,
            productId = it.productId,
            savedOrder
        )
    }

    private fun createNewPayment(
        command: RegisterOrderUseCase.RegisterOrderCommand,
        savedOrder: Order
    ) = Payment(
        type = command.paymentCommand.type,
        company = command.paymentCommand.company,
        number = command.paymentCommand.number,
        username = command.paymentCommand.username,
        savedOrder
    )

    private fun calculateTotalCount(newOrderItems: List<OrderItem>): Long {
        var totalPrice = 0L
        newOrderItems.forEach {
            totalPrice += it.price * it.count
        }

        return totalPrice
    }
}