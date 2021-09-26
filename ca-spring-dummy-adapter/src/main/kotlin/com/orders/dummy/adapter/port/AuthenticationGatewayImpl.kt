package com.orders.dummy.adapter.port

import com.authentication.domain.Credentials
import com.authentication.domain.CredentialsType
import com.orders.domain.Country
import com.orders.domain.Owner
import com.orders.domain.OwnerType
import com.orders.usecase.exception.InvalidCredentialsException
import com.orders.usecase.port.AuthenticationGateway

class AuthenticationGatewayImpl : AuthenticationGateway {

    private object AuthenticationConstants {

        const val storeKey = "EddkthmlpMs415rLgsO42bmBZMcR9kIwJVd72jEm"

        const val userJwt =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"

        val store: Owner = Owner.builder()
            .withCountry(Country.AR)
            .withType(OwnerType.STORE)
            .withUniqueID("e724b5d1-27b3-4dfe-b130-bb6ac5443332")
            .build()

        val user: Owner = Owner.builder()
            .withCountry(Country.AR)
            .withType(OwnerType.USER)
            .withUniqueID("f859aaad-6181-45e3-9159-0c81e851e30a")
            .build()
    }

    override fun validateAndGet(credentials: Credentials): Owner {
        if (credentials.type == CredentialsType.API_KEY && credentials.value == AuthenticationConstants.storeKey) {
            return AuthenticationConstants.store
        }
        if (credentials.type == CredentialsType.JWT && credentials.value == "Bearer ${AuthenticationConstants.userJwt}") {
            return AuthenticationConstants.user
        }
        throw InvalidCredentialsException(credentials)
    }
}