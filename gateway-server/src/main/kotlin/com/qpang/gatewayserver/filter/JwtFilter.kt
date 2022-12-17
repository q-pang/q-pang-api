package com.qpang.gatewayserver.filter

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class JwtFilter : AbstractGatewayFilterFactory<JwtFilter.Config>(Config::class.java) {
    val log: Logger = LoggerFactory.getLogger(this::class.java)

    override fun apply(config: Config): GatewayFilter {
        return GatewayFilter { exchange, chain ->
            val request = exchange.request
            val response = exchange.response

            log.info("Global request id: ${request.id}")
            chain.filter(exchange).then(Mono.fromRunnable {
                log.info("Global response status code: ${response.statusCode}")
            })
        }
    }

    class Config
}