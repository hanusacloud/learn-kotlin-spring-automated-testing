package com.learn.automated.testing.demo.features.promo.repositories

import com.learn.automated.testing.demo.features.promo.models.Promo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PromoRepository : JpaRepository<Promo, Long>