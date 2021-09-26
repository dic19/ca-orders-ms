package com.orders.jpa.adapter

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories
@EntityScan
@PropertySource("classpath:ca-orders-jpa-adapter.properties")
class SpringJpaAdapterConfiguration