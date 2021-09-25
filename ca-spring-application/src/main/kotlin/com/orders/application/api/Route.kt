package com.orders.application.api

object Route {

    const val ORDERS_PATH = "/orders"
    const val ORDERS_BY_ID_PATH = "${ORDERS_PATH}/{uniqueID}"
    const val PRODUCTS_PATH = "${ORDERS_BY_ID_PATH}/products"
    const val PRODUCTS_BY_CODE_PATH = "${PRODUCTS_PATH}/{productCode}"
}