package com.qpang.orderservice.application.port.out.event

interface EventProducePort {
    fun sendMessage(message: String)
}