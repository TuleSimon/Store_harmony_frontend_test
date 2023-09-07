package com.simon.storeharmonytest.data.sources.network

import com.simon.storeharmonytest.data.dto.OrderDTO
import com.simon.storeharmonytest.data.dto.OrderResponseDTO
import com.simon.storeharmonytest.data.dto.ProductsDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface StoreHarmonyApi {

    @GET("stores/{store}/products")
    suspend fun getProducts(
        @Path("store") store:String,
        @Query("offset") offset:Int,
        @Query("size") size:Int,
    ):List<ProductsDTO>

    @POST("stores/{store}/orders")
    suspend fun placeOrder(
        @Path("store") store:String,
        @Body() order:OrderDTO,
    ):OrderResponseDTO
}