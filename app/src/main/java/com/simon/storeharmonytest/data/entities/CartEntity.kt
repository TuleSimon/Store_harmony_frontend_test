package com.simon.storeharmonytest.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
const val CART_TABLE = "cart"

@Entity(tableName = CART_TABLE )
data class CartEntity(
    val category: String,
    val description: String,
    @PrimaryKey(autoGenerate = true)
    val primaryKey:Int= 0,
    val id: Int,
    val imageUrl: String,
    val name: String,
    val sku: String,
    val unitPrice: Double,
    val unitsInStock: Int,
    val quantity:Int,
    val totalPrice:Double
)
