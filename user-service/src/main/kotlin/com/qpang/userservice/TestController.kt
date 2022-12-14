package com.qpang.userservice

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {

    @GetMapping("/test")
    fun test(): ResponseEntity<String> {
        return ResponseEntity.ok().body("user")
    }

    @GetMapping("/user/test")
    fun user(): ResponseEntity<String> {
        return ResponseEntity.ok().body("/user/test")
    }

}