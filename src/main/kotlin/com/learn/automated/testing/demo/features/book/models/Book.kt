package com.learn.automated.testing.demo.features.book.models

import com.learn.automated.testing.demo.features.category.models.Category
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "book")
@EntityListeners(AuditingEntityListener::class)
class Book (
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private var id: Long? = null,

        @Column(name ="title")
        private var title: String,

        @Column(name = "total_page")
        private var totalPage: Int,

        @Column(name = "created_at")
        @CreatedDate
        private var createdAt: Date? = null,

        @Column(name = "updated_at")
        @LastModifiedDate
        private var updatedAt: Date? = null,

        @Column(name = "price")
        private var price: Long,

        @ManyToOne
        @JoinColumn(name = "category_id")
        private var category: Category
) {

        fun setTitle(title: String) {
                this.title = title
        }

        fun setTotalPage(totalPage: Int) {
                this.totalPage = totalPage
        }

        fun setPrice(price: Long) {
                this.price = price
        }

        fun getId(): Long? = id

        fun getTitle(): String = title

        fun getTotalPage(): Int = totalPage

        fun getCreatedAt(): Date? = createdAt

        fun getUpdatedAt(): Date? = updatedAt

        fun getPrice(): Long = price

        fun getCategory(): Category = category

}