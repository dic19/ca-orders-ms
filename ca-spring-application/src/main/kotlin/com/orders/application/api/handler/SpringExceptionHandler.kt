package com.orders.application.api.handler

import com.orders.application.api.model.ApiErrorResponse
import com.orders.usecase.exception.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageConversionException
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import java.util.stream.Collectors


@ControllerAdvice
class SpringExceptionHandler {

    @Value("\${spring.application.name:}")
    private lateinit var applicationName: String

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handle(ex: HttpMessageConversionException, request: WebRequest): ResponseEntity<ApiErrorResponse> {
        val errorCode = prefix("validation.invalid-request-body")
        val message = "Request body has an invalid [${request.getHeader(HttpHeaders.CONTENT_TYPE)}] format"
        return this.createResponse(HttpStatus.BAD_REQUEST, errorCode, message, ex)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handle(ex: MethodArgumentNotValidException, request: WebRequest): ResponseEntity<ApiErrorResponse> {
        val errorCode = prefix("validation.invalid-field-value")
        val message = ex.bindingResult
            .fieldErrors
            .stream()
            .map { error: FieldError ->
                "Invalid value [${error.rejectedValue}] for request field [${error.field}]."
            }
            .collect(Collectors.joining(" | "))
        return this.createResponse(HttpStatus.BAD_REQUEST, errorCode, message, ex)
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handle(ex: MethodArgumentTypeMismatchException, request: WebRequest): ResponseEntity<ApiErrorResponse> {
        val errorCode = prefix("validation.invalid-field-value")
        val message = "Invalid value [${ex.value}] for request field [${ex.name}]."
        return this.createResponse(HttpStatus.BAD_REQUEST, errorCode, message, ex)
    }

    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun handle(ex: MissingServletRequestParameterException, request: WebRequest): ResponseEntity<ApiErrorResponse> {
        val errorCode = prefix("validation.invalid-field-value")
        val message = "Invalid value [null] for request field [${ex.parameterName}]."
        return this.createResponse(HttpStatus.BAD_REQUEST, errorCode, message, ex)
    }

    @ExceptionHandler(AbstractUseCaseException::class)
    fun handle(ex: AbstractUseCaseException, request: WebRequest): ResponseEntity<ApiErrorResponse> {
        val status = when (ex) {
            is InvalidCredentialsException -> HttpStatus.UNAUTHORIZED
            is FieldsValidationException -> HttpStatus.BAD_REQUEST
            is OrderNotFoundException -> HttpStatus.NOT_FOUND
            is ProductNotFoundException -> HttpStatus.NOT_FOUND
            is UseCaseExecutionException -> HttpStatus.CONFLICT
            else -> HttpStatus.INTERNAL_SERVER_ERROR
        }
        val errorCode = prefix(ex.errorCode)
        val message = ex.message.orEmpty()
        return this.createResponse(status, errorCode, message, ex)
    }

    @ExceptionHandler(RuntimeException::class)
    fun handle(ex: RuntimeException, request: WebRequest): ResponseEntity<ApiErrorResponse> {
        val status = HttpStatus.INTERNAL_SERVER_ERROR
        val errorCode = prefix("internal-server-error")
        val message = ex.message.orEmpty()
        return this.createResponse(status, errorCode, message, ex)
    }

    protected fun createResponse(
        status: HttpStatus,
        errorCode: String,
        errorMessage: String,
        cause: Throwable
    ): ResponseEntity<ApiErrorResponse> {
        val body = ApiErrorResponse(
            error = ApiErrorResponse.ErrorView(
                code = errorCode,
                message = errorMessage,
                cause = cause.message.orEmpty()
            )
        )
        return ResponseEntity.status(status).body(body)
    }

    protected fun prefix(errorCode: String): String {
        return "${applicationName}.${errorCode}";
    }
}