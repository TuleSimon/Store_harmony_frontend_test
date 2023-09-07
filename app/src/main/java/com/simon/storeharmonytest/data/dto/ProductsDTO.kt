package com.simon.storeharmonytest.data.dto


import com.google.gson.annotations.SerializedName

data class ProductsDTO(
    @SerializedName("category")
    val category: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("sku")
    val sku: String,
    @SerializedName("unitPrice")
    val unitPrice: Double,
    @SerializedName("unitsInStock")
    val unitsInStock: Int
)