package com.orders.usecase;

import com.authentication.domain.Credentials;

import java.util.UUID;

public interface DeleteOrderUseCase {

    void execute(Credentials credentials, UUID uniqueID);
}
