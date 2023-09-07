package com.simon.storeharmonytest.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.simon.storeharmonytest.data.sources.network.StoreHarmonyApi
import com.simon.storeharmonytest.utils.ApiKeys
import com.simon.storeharmonytest.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Collections
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule  {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .disableHtmlEscaping().create()
    }

    @Provides
    @Singleton
    fun provideHttpClient(
        authInterceptor: ApiHeaderInterceptor,
    ): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
        okHttpClientBuilder
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().also { it.level = HttpLoggingInterceptor.Level.BODY } )
            .addInterceptor(authInterceptor)
            .protocols(Collections.singletonList(Protocol.HTTP_1_1))
        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        factory: Gson,
        client: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory( CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(factory))
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideStoreHarmonyApi(retrofit:Retrofit):StoreHarmonyApi{
        return retrofit.create(StoreHarmonyApi::class.java)
    }

}

class ApiHeaderInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request()
                .newBuilder()
                .addHeader("X-API-KEY", ApiKeys.API_KEY)
                .build()
        )
    }
}

