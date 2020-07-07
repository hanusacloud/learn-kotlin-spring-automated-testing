package com.learn.automated.testing.demo.features.promo.models

import com.learn.automated.testing.demo.features.book.models.Book
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "promo")
@EntityListeners(AuditingEntityListener::class)
class Promo (
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long? = null,

        @ManyToOne
        @JoinColumn(name = "book_id")
        var book: Book,

        @Column(name = "promo_price")
        var promoPrice: Long,

        @Column(name = "start_date")
        var startDate: Date,

        @Column(name = "end_date")
        var endDate: Date,

        @Column(name = "created_at")
        @CreatedDate
        var createdAt: Date? = null,

        @Column(name = "updated_at")
        @LastModifiedDate
        var updatedAt: Date? = null
)