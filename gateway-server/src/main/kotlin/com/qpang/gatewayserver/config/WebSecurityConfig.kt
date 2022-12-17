package com.qpang.gatewayserver.config

import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain

@EnableWebFluxSecurity
class WebSecurityConfig {
    @Bean
    open fun filterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        http
            .csrf().disable()
            .formLogin().disable()
            .httpBasic().disable()
            
            .authorizeExchange()
            .pathMatchers("/user/signup", "/user/signin").permitAll()
            .anyExchange().authenticated()

        return http.build()
    }
}