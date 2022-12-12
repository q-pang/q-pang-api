package com.qpang.gatewayserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GatewayServerApplication

fun main(args: Array<String>) {
	runApplication<GatewayServerApplication>(*args)
}
