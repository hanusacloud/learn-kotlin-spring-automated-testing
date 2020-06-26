package com.learn.automated.testing.demo.integrations

import com.fasterxml.jackson.databind.ObjectMapper
import com.learn.automated.testing.demo.DemoApplication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [DemoApplication::class])
open class BaseIntegration {

    @Autowired
    protected lateinit var restTemplate: TestRestTemplate
    @LocalServerPort
    protected lateinit var port: Number

    protected fun getUrl(path: String): String = "http://localhost:${port}" + path

}