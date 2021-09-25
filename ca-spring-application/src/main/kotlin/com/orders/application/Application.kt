package com.orders.application

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication(scanBasePackages = ["com.orders"])
class Application

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}
