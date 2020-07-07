package com.learn.automated.testing.demo.features.category.exceptions

import com.learn.automated.testing.demo.shared.utils.Translation

class CategoryException(message: String?) : Exception(message) {

    companion object {

        fun notFound(): CategoryException = CategoryException(Translation.getMessage("category.not.found"))

    }

}