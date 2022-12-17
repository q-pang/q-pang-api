package com.qpang.gatewayserver.filter

import com.qpang.gatewayserver.exception.JwtValidateException
import com.qpang.gatewayserver.util.JwtUtil
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.http.server.RequestPath
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class JwtFilter(val jwtUtil: JwtUtil) : AbstractGatewayFilterFactory<JwtFilter.Config>(Config::class.java) {
    val log: Logger = LoggerFactory.getLogger(this::class.java)

    override fun apply(config: Config): GatewayFilter {
        return GatewayFilter { exchange, chain ->
            val request = exchange.request
            val response = exchange.response
            log.info("Global request id: ${request.id}")
            if (!isPermitPath(request.path)) {
                val token =
                    jwtUtil.getToken(
                        request.headers["Authorization"]?.get(0)
                            ?: throw JwtValidateException("empty Authorization Header")
                    )
                val claims = jwtUtil.resolveToken(token)
                addHeaders(request, "username", claims.subject)
            }

            chain.filter(exchange).then(Mono.fromRunnable {
                log.info("Global response status code: ${response.statusCode}")
            })
        }
    }

    private fun isPermitPath(path: RequestPath): Boolean {
        val pathToString = path.toString()
        return API_WHITELIST_GUEST.contains(pathToString)
    }

    private fun addHeaders(
        request: ServerHttpRequest,
        headerName: String,
        username: String
    ) {
        request.mutate().header(headerName, username)
    }

    class Config

    companion object{
        val API_WHITELIST_GUEST = listOf<String>("/user/signup", "/user/signin")
    }
}