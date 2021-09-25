package com.orders.usecase.port;

import com.authentication.domain.Credentials;
import com.orders.domain.Owner;

public interface AuthenticationGateway {

    Owner validateAndGet(Credentials credentials);
}
