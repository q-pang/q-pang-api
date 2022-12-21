package com.qpang.orderservice.infrastructure.external

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class PaymentModule {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)
    fun payment(name: String, typeName: String, companyName: String, number: String, amount: Long) {
        log.info("execute payment module : $name, $typeName, $companyName, $number, $amount")
    }
}