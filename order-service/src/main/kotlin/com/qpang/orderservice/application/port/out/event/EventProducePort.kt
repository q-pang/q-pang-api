package com.qpang.orderservice.application.port.out.event

import com.qpang.orderservice.application.port.out.event.dto.CancelOrderEvent
import com.qpang.orderservice.application.port.out.event.dto.OrderEvent

interface EventProducePort {
    fun order(orderEvent: OrderEvent)
    fun cancelOrder(cancelOrderEvent: CancelOrderEvent)
}