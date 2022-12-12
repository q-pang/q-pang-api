package com.qpang.productservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ProductServiceApplication

fun main(args: Array<String>) {
	runApplication<ProductServiceApplication>(*args)
}
