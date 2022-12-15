package com.qpang.userservice.domain

import com.qpang.userservice.common.entity.JpaAuditEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "users")
class User(
    name: String
) : JpaAuditEntity() {
    @Column(name = "name", nullable = false)
    var name: String = name
        protected set
}