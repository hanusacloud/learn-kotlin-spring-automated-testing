package com.learn.automated.testing.demo.features.category.models

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "category")
@EntityListeners(AuditingEntityListener::class)
class Category (
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private var id: Long? = null,

        @Column(name = "name")
        private var name: String,

        @Column(name = "created_at")
        @CreatedDate
        private var createdAt: Date? = null,

        @Column(name = "updated_at")
        @LastModifiedDate
        private var updatedAt: Date? = null
) {

        fun getId(): Long? = id

        fun getName(): String = name

}