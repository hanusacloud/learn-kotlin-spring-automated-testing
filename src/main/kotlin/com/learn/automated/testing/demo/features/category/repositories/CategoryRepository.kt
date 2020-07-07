package com.learn.automated.testing.demo.features.category.repositories

import com.learn.automated.testing.demo.features.category.models.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : JpaRepository<Category, Long>