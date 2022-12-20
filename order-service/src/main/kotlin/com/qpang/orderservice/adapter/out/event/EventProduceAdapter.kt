package com.qpang.orderservice.adapter.out.event

import com.qpang.orderservice.application.port.out.event.EventProducePort
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class EventProduceAdapter(
    private val kafkaTemplate: KafkaTemplate<String, String>
) : EventProducePort {
    val log: Logger = LoggerFactory.getLogger(this::class.java)

    override fun sendMessage(message: String) {
        log.info("Produce message : {}", message);
        kafkaTemplate.send(TOPIC, message);
    }

    companion object {
        const val TOPIC: String = "exam-topic"
    }
}