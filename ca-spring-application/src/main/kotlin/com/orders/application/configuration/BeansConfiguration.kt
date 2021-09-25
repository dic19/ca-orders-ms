package com.orders.application.configuration

import com.orders.usecase.*
import com.orders.usecase.port.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BeansConfiguration(
    private val authenticationGateway: AuthenticationGateway,
    private val orderEventSender: OrderEventSender
) {

    @Bean
    fun addProductsUseCase(addProductsRepository: AddProductsRepository): AddProductsUseCase {
        return AddProductsUseCaseImpl(authenticationGateway, addProductsRepository, orderEventSender)
    }

    @Bean
    fun createOrderUseCase(createOrderRepository: CreateOrderRepository): CreateOrderUseCase {
        return CreateOrderUseCaseImpl(authenticationGateway, createOrderRepository, orderEventSender)
    }

    @Bean
    fun deleteOrderUseCase(deleteOrderRepository: DeleteOrderRepository): DeleteOrderUseCase {
        return DeleteOrderUseCaseImpl(authenticationGateway, deleteOrderRepository, orderEventSender)
    }

    @Bean
    fun dropProductUseCase(dropProductRepository: DropProductRepository): DropProductUseCase {
        return DropProductUseCaseImpl(authenticationGateway, dropProductRepository, orderEventSender)
    }

    @Bean
    fun findOrderUseCase(findOrderRepository: FindOrderRepository): FindOrderUseCase {
        return FindOrderUseCaseImpl(authenticationGateway, findOrderRepository)
    }

    @Bean
    fun getOrderUseCase(getOrderRepository: GetOrderRepository): GetOrderUseCase {
        return GetOrderUseCaseImpl(authenticationGateway, getOrderRepository)
    }

    @Bean
    fun updateOrderUseCase(updateOrderRepository: UpdateOrderRepository): UpdateOrderUseCase {
        return UpdateOrderUseCaseImpl(authenticationGateway, updateOrderRepository, orderEventSender)
    }
}