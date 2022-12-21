package com.qpang.orderservice.infrastructure.external

import com.github.f4b6a3.ulid.UlidCreator
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class PaymentModule {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)
    fun payment(name: String, typeName: String, companyName: String, number: String, amount: Long): String {
        log.info("execute payment module : $name, $typeName, $companyName, $number, $amount")
        return UlidCreator.getMonotonicUlid().toUuid().toString()
    }
}