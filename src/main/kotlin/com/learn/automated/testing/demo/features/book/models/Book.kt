package com.learn.automated.testing.demo.features.book.models

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "book")
@EntityListeners(AuditingEntityListener::class)
class Book (
        @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Long? = null,
        @Column(name ="title") var title: String,
        @Column(name = "total_page") var totalPage: Int,
        @Column(name = "created_at") @CreatedDate var createdAt: Date? = null,
        @Column(name = "updated_at") @LastModifiedDate var updatedAt: Date? = null
)