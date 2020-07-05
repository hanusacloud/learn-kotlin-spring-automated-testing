package com.learn.automated.testing.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
class DemoApplication {

    fun main(args: Array<String>) {
        runApplication<DemoApplication>(*args)
    }

}
