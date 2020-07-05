package com.learn.automated.testing.demo.features.promo.exceptions

import com.learn.automated.testing.demo.features.book.exceptions.BookException
import com.learn.automated.testing.demo.shared.utils.Translation

class PromoException(message: String?) : Exception(message) {

    companion object {

        fun promoPriceExceedsOriginal(): BookException =
                BookException(Translation.getMessage("promo.price.exceeds.original.price"))

    }

}