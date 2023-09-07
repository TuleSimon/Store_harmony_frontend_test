package com.simon.storeharmonytest.data.mappers

import com.simon.storeharmonytest.data.dto.ProductsDTO
import com.simon.storeharmonytest.data.entities.CartEntity

data class Products(
    val category: String,
    val description: String,
    val id: Int,
    val imageUrl: String,
    val name: String,
    val sku: String,
    val unitPrice: Double,
    val unitsInStock: Int
)

data class  CartProduct(
    val category: String,
    val description: String,
    val id: Int,
    val imageUrl: String,
    val name: String,
    val sku: String,
    val unitPrice: Double,
    val unitsInStock: Int,
    val quantity:Int,
    val key:Int,
    val totalPrice:Double
)

fun CartEntity.toCartProduct():CartProduct{
    return CartProduct(category, description, id, imageUrl, name, sku,unitPrice, unitsInStock, quantity,primaryKey,totalPrice)
}

fun ProductsDTO.toProducts(): Products{
    return Products(category, description, id, imageUrl, name, sku,unitPrice, unitsInStock)
}