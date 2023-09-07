package com.simon.storeharmonytest.repositories

import com.simon.storeharmonytest.data.dto.OrderDTO
import com.simon.storeharmonytest.data.dto.OrderResponseDTO
import com.simon.storeharmonytest.data.mappers.CartProduct
import com.simon.storeharmonytest.data.mappers.Products
import com.simon.storeharmonytest.data.models.UserProfile
import com.simon.storeharmonytest.data.sources.network.NetworkResponse
import com.simon.storeharmonytest.data.sources.network._NetworkDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NetworkRepository @Inject constructor(private val networkDataSource: _NetworkDataSource) {

    suspend fun getProducts(offset: Int, size: Int): Flow<NetworkResponse<List<Products>>> {
        return networkDataSource.getProducts(offset, size)
    }

    suspend fun placeOrder(
        profile: UserProfile,
        vararg cartItems: CartProduct
    ): Flow<NetworkResponse<OrderResponseDTO>> {

        val order = OrderDTO(
            billingAddress = OrderDTO.BillingAddress(
                city = profile.city,
                country = profile.country,
                state = profile.state,
                street = profile.city
            ),
            customerEmail = profile.email,
            customerFirstName = profile.firstName,
            customerLastName = profile.lastName,
            orderItems = cartItems.map { OrderDTO.OrderItem(it.id, it.quantity, it.unitPrice) },
            shippingAddress = OrderDTO.ShippingAddress(
                city = profile.city,
                country = profile.country,
                state = profile.state,
                street = profile.city
            ),
            totalPrice = cartItems.sumOf { it.unitPrice * it.quantity },
            totalQuantity = cartItems.sumOf { it.quantity }
        )
        return networkDataSource.placeOrder(order)
    }

}