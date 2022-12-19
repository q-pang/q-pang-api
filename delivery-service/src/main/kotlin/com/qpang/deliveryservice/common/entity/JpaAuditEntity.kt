package com.qpang.deliveryservice.common.entity

import com.qpang.productservice.common.entity.PrimaryKeyEntity
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class JpaAuditEntity : PrimaryKeyEntity() {
    @CreatedDate
    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.MIN
        protected set

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.MIN
        protected set
}