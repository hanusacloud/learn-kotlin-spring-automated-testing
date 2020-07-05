package com.learn.automated.testing.demo.features.promo.services

import com.learn.automated.testing.demo.features.promo.reponse.PromoDetailResponse
import com.learn.automated.testing.demo.features.promo.requests.PromoRequest

interface PromoService {

    fun create(promoRequest: PromoRequest): PromoDetailResponse

}