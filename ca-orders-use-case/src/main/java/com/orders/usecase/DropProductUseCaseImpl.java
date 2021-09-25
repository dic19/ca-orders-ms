package com.orders.usecase;

import com.authentication.domain.Credentials;
import com.orders.domain.Order;
import com.orders.domain.OrderEventType;
import com.orders.usecase.exception.FieldsValidationException;
import com.orders.usecase.exception.InvalidCredentialsException;
import com.orders.usecase.port.AuthenticationGateway;
import com.orders.usecase.port.DropProductRepository;
import com.orders.usecase.port.OrderEventSender;

import java.util.Objects;
import java.util.UUID;

import static com.orders.usecase.validation.Validators.*;

public class DropProductUseCaseImpl implements DropProductUseCase {

    private final AuthenticationGateway authenticationGateway;
    private final DropProductRepository productRepository;
    private final OrderEventSender eventSender;

    public DropProductUseCaseImpl(AuthenticationGateway authenticationGateway, DropProductRepository productRepository, OrderEventSender eventSender) {
        this.authenticationGateway = authenticationGateway;
        this.productRepository = productRepository;
        this.eventSender = eventSender;
    }

    @Override
    public Order execute(final Credentials credentials, final UUID uniqueID, final String productCode) {
        if (Objects.isNull(credentials) || notBlankValidator("credentials", credentials.getValue()).rejected()) {
            throw new InvalidCredentialsException(credentials);
        }

        final var validator = compose(notNullValidator("uniqueID", uniqueID)
            , notBlankValidator("productCode", productCode));

        if (validator.rejected()) {
            throw new FieldsValidationException(validator.validate());
        }

        final var owner = this.authenticationGateway.validateAndGet(credentials);
        final var order = this.productRepository.dropProduct(owner, uniqueID, productCode);
        this.eventSender.send(OrderEventType.UPDATED, order);
        return order;
    }
}
