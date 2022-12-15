package com.qpang.userservice.config

import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsUtils

@EnableWebSecurity
class WebSecurityConfig {

    @Bean
    open fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf().disable()
            .formLogin().disable()
            .httpBasic().disable()
            .authorizeRequests { authorize ->
                authorize
                    .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                    .antMatchers("/user/signup").permitAll()
                    .anyRequest().authenticated()
            }

        return http.build()
    }
}