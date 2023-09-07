package com.simon.storeharmonytest.data.dto


import com.google.gson.annotations.SerializedName

data class OrderDTO(
    @SerializedName("billingAddress")
    val billingAddress: BillingAddress,
    @SerializedName("customerEmail")
    val customerEmail: String,
    @SerializedName("customerFirstName")
    val customerFirstName: String,
    @SerializedName("customerLastName")
    val customerLastName: String,
    @SerializedName("orderItems")
    val orderItems: List<OrderItem>,
    @SerializedName("shippingAddress")
    val shippingAddress: ShippingAddress,
    @SerializedName("totalPrice")
    val totalPrice: Double,
    @SerializedName("totalQuantity")
    val totalQuantity: Int
) {
    data class BillingAddress(
        @SerializedName("city")
        val city: String,
        @SerializedName("country")
        val country: String,
        @SerializedName("state")
        val state: String,
        @SerializedName("street")
        val street: String
    )

    data class OrderItem(
        @SerializedName("productId")
        val productId: Int,
        @SerializedName("quantity")
        val quantity: Int,
        @SerializedName("unitPrice")
        val unitPrice: Double
    )

    data class ShippingAddress(
        @SerializedName("city")
        val city: String,
        @SerializedName("country")
        val country: String,
        @SerializedName("state")
        val state: String,
        @SerializedName("street")
        val street: String
    )
}