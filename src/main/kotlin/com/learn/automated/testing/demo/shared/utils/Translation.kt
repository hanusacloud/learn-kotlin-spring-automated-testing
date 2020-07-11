package com.learn.automated.testing.demo.shared.utils

import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.stereotype.Component
import java.util.*

@Component
class Translation {

    companion object {

        private val messageResource: ResourceBundleMessageSource = initMessageResource()

        private fun initMessageResource():  ResourceBundleMessageSource {
            val messageResource = ResourceBundleMessageSource()
            messageResource.setBasename("messages")
            messageResource.setCacheSeconds(3600)
            return messageResource
        }

        fun getMessage(message: String): String  {
            val locale: Locale = LocaleContextHolder.getLocale()
            return messageResource.getMessage(message, null, message, locale)
        }

    }

}