package com.learn.automated.testing.demo.features.book.models

import javax.persistence.*

@Entity
@Table(name = "book")
class Book (
        @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Long,
        var title: String,
        var totalPage: Int
)