package com.qpang.orderservice.adapter.out.event

import com.qpang.orderservice.application.port.out.event.EventProducePort
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class EventProduceAdapter(
) : EventProducePort {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    override fun sendMessage(message: String) {
        log.info("Produce message : {}", message);
    }
}