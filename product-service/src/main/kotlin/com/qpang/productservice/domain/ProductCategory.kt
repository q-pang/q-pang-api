package com.qpang.productservice.domain

import com.qpang.productservice.common.entity.JpaAuditEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "product_categories")
class ProductCategory(
    name: String
) : JpaAuditEntity() {
    @Column(name = "name", nullable = false)
    var name: String = name
        protected set
}