package com.learn.automated.testing.demo.features.promo.reponse

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.learn.automated.testing.demo.features.promo.models.Promo
import com.learn.common.features.book.BookResponse
import com.learn.common.features.category.CategoryResponse
import com.learn.common.shared.BaseResponse

class PromoDetailResponse : BaseResponse {

    @JsonProperty("promo")
    private val promo: PromoResponse?

    @JsonCreator
    constructor(
            @JsonProperty("status") status: Boolean,
            @JsonProperty("message") message: String,
            @JsonProperty("promo") promo: PromoResponse?
    ) : super (status, message) {
        this.promo = promo
    }

    constructor(
            status: Boolean,
            message: String,
            promo: Promo
    ) : super (status, message) {
        this.promo = PromoResponse(
                promo.id!!,
                promo.promoPrice,
                BookResponse(
                        promo.book.getId()!!,
                        promo.book.getTitle(),
                        promo.book.getTotalPage(),
                        promo.book.getCreatedAt(),
                        promo.book.getPrice(),
                        CategoryResponse(
                                promo.book.getCategory().getId()!!,
                                promo.book.getCategory().getName()
                        )
                ),
                promo.startDate,
                promo.endDate
        )
    }

    fun getPromo(): PromoResponse? = promo

}