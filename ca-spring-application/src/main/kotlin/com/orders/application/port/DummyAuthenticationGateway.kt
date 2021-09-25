package com.orders.application.port

import com.authentication.domain.Credentials
import com.authentication.domain.CredentialsType
import com.orders.domain.Country
import com.orders.domain.Owner
import com.orders.domain.OwnerType
import com.orders.usecase.exception.InvalidCredentialsException
import com.orders.usecase.port.AuthenticationGateway
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class DummyAuthenticationGateway(
    @Value("\${microservice.store.key}")
    private val storeKey: String,

    @Value("\${microservice.user.jwt}")
    private val userJwt: String

) : AuthenticationGateway {

    private lateinit var store: Owner
    private lateinit var user: Owner

    @PostConstruct
    fun postConstruct() {
        this.store = Owner.builder()
            .withCountry(Country.AR)
            .withType(OwnerType.STORE)
            .withUniqueID("e724b5d1-27b3-4dfe-b130-bb6ac5443332")
            .build()

        this.user = Owner.builder()
            .withCountry(Country.AR)
            .withType(OwnerType.USER)
            .withUniqueID("f859aaad-6181-45e3-9159-0c81e851e30a")
            .build()
    }

    override fun validateAndGet(credentials: Credentials): Owner {
        if (credentials.type == CredentialsType.API_KEY && credentials.value == storeKey) {
            return store
        }
        if (credentials.type == CredentialsType.JWT && credentials.value == "Bearer $userJwt") {
            return user
        }
        throw InvalidCredentialsException(credentials)
    }
}