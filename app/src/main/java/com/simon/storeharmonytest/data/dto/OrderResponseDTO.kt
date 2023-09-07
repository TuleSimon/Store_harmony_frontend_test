package com.simon.storeharmonytest.data.dto


import com.google.gson.annotations.SerializedName

data class OrderResponseDTO(
    @SerializedName("billingAddress")
    val billingAddress: BillingAddress,
    @SerializedName("customerEmail")
    val customerEmail: String,
    @SerializedName("customerFirstName")
    val customerFirstName: String,
    @SerializedName("customerLastName")
    val customerLastName: String,
    @SerializedName("dateCreated")
    val dateCreated: Long,
    @SerializedName("id")
    val id: Int,
    @SerializedName("lastUpdated")
    val lastUpdated: Long,
    @SerializedName("orderItems")
    val orderItems: List<OrderItem>,
    @SerializedName("orderTrackingNumber")
    val orderTrackingNumber: String,
    @SerializedName("shippingAddress")
    val shippingAddress: ShippingAddress,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalPrice")
    val totalPrice: Int,
    @SerializedName("totalQuantity")
    val totalQuantity: Int
) {
    data class BillingAddress(
        @SerializedName("city")
        val city: String,
        @SerializedName("country")
        val country: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("state")
        val state: String,
        @SerializedName("street")
        val street: String
    )

    data class OrderItem(
        @SerializedName("id")
        val id: Int,
        @SerializedName("productId")
        val productId: Int,
        @SerializedName("quantity")
        val quantity: Int,
        @SerializedName("unitPrice")
        val unitPrice: Int
    )

    data class ShippingAddress(
        @SerializedName("city")
        val city: String,
        @SerializedName("country")
        val country: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("state")
        val state: String,
        @SerializedName("street")
        val street: String
    )
}