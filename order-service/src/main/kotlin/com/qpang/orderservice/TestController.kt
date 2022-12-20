package com.qpang.orderservice

import com.qpang.orderservice.application.port.out.event.EventProducePort
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController(
    private val eventProducePort: EventProducePort
) {
    val log: Logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping("/order/publish")
    fun publishEvent() {
        log.info("------------------------------------------publishEvenet Start------------------------------------------")
        eventProducePort.sendMessage("Test Message")
        log.info("------------------------------------------publishEvenet End------------------------------------------")
    }
}