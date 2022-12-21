package com.qpang.productservice.adapter.`in`.event

import com.qpang.productservice.adapter.`in`.event.dto.OrderEvent
import com.qpang.productservice.application.port.`in`.usecase.UpdateStockUseCase
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class OrderEventAdapter(
    private val updateStockUseCase: UpdateStockUseCase
) {
    @KafkaListener(topics = ["order"])
    fun consume(event: OrderEvent) {
        for (i in event.productList.indices) {
            val updateProduct = event.productList[i]
            val updateStockCommand = UpdateStockUseCase.UpdateStockCommand(
                id = updateProduct.id,
                updatedStock = -updateProduct.count
            )
            updateStockUseCase.command(updateStockCommand)
        }
    }
}