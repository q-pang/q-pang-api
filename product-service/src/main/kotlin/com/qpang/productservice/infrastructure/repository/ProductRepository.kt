package com.qpang.productservice.infrastructure.repository

import com.qpang.productservice.domain.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, String> {
}