package com.simon.storeharmonytest.data.sources.network

import com.simon.storeharmonytest.data.dto.OrderDTO
import com.simon.storeharmonytest.data.dto.OrderResponseDTO
import com.simon.storeharmonytest.data.mappers.Products
import com.simon.storeharmonytest.data.mappers.toProducts
import com.simon.storeharmonytest.utils.ApiKeys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class NetworkDataSource @Inject constructor(private val storeHarmonyApi: StoreHarmonyApi) :
    _NetworkDataSource {

    override suspend fun getProducts(
        offset: Int,
        size: Int
    ): Flow<NetworkResponse<List<Products>>> = flow {

        val products = storeHarmonyApi.getProducts(ApiKeys.STORE_ID, offset, size)
        emit(NetworkResponse.Success(products.map { it.toProducts() }))
    }.defaultHandler()

    override suspend fun placeOrder(order: OrderDTO): Flow<NetworkResponse<OrderResponseDTO>> =
        flow {
            val order = storeHarmonyApi.placeOrder(ApiKeys.STORE_ID, order)
            emit(NetworkResponse.Success(order))
        }.defaultHandler()

}

sealed class NetworkResponse<out T>() {
    object Loading : NetworkResponse<Nothing>()
    object Error : NetworkResponse<Nothing>()
    data class Success<out T>(val data: T) : NetworkResponse<T>()
}

fun <T> Flow<NetworkResponse<T>>.defaultHandler(): Flow<NetworkResponse<T>> {
    return catch {
        if (it is UnknownHostException || it is SocketTimeoutException) {
            emit(NetworkResponse.Error)
        } else {
            it.printStackTrace()
            emit(
                NetworkResponse.Error,
            )
        }
    }.onStart {
        emit(NetworkResponse.Loading)
    }
}

interface _NetworkDataSource {
    suspend fun getProducts(offset: Int, size: Int): Flow<NetworkResponse<List<Products>>>
    suspend fun placeOrder(order: OrderDTO): Flow<NetworkResponse<OrderResponseDTO>>
}