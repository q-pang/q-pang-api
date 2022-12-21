package com.qpang.orderservice.adapter.out.event

import com.qpang.orderservice.application.port.out.event.EventProducePort
import com.qpang.orderservice.application.port.out.event.dto.CancelOrderEvent
import com.qpang.orderservice.application.port.out.event.dto.OrderEvent
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class EventProduceAdapter(
    private val kafkaTemplate: KafkaTemplate<String, Any>
):EventProducePort {
    override fun order(orderEvent: OrderEvent) {
        kafkaTemplate.send("order", orderEvent)
    }

    override fun cancelOrder(cancelOrderEvent: CancelOrderEvent) {
        kafkaTemplate.send("cancel-order", cancelOrderEvent)
    }
}