package com.qpang.productservice.adapter.`in`.event

import com.qpang.productservice.adapter.`in`.event.dto.CancelOrderEvent
import com.qpang.productservice.application.port.`in`.usecase.UpdateStockUseCase
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class CancelOrderEventAdapter(
    private val updateStockUseCase: UpdateStockUseCase
) {
    @KafkaListener(topics = ["cancel-order"])
    fun consume(event: CancelOrderEvent) {
        // TODO: 상품 리스트를 command로 사용하는 Application Service 추가하여 반복적인 command 실행 대체
        for (i in event.productList.indices) {
            val updateProduct = event.productList[i]
            val updateStockCommand = UpdateStockUseCase.UpdateStockCommand(
                id = updateProduct.id,
                updatedStock = updateProduct.count
            )
            updateStockUseCase.command(updateStockCommand)
        }
    }
}