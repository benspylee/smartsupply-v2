package com.asuravan.smartsupply.client

import com.asuravan.smartsupply.utils.BluebeeUtils
import com.asuravan.smartsupply.utils.BluebeeUtils.BASE_URL
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class RetroRestClient<T> {
  private val loggingInterceptor=HttpLoggingInterceptor();
    init {
        loggingInterceptor.level =HttpLoggingInterceptor.Level.BODY;
    }

    private val client =  OkHttpClient.Builder()
        .addInterceptor(BasicAuthInterceptor(BluebeeUtils.USER_NAME, BluebeeUtils.PASSWORD))
        .addInterceptor(loggingInterceptor)
   /*     .addInterceptor(object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                val request: Request = chain.request()
                val response: okhttp3.Response = chain.proceed(request)
                if (response.code === 403) {

                }
                return response
            }
        })*/
        .build()

    val gson = GsonBuilder()
        .setLenient()
        .create();

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    fun create(service: Class<T>): T {
        return retrofit.create(service)
    }
}