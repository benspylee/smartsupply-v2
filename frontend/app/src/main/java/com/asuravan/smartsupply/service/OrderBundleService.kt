package com.asuravan.smartsupply.service

import com.asuravan.smartsupply.pojo.AppUser
import com.asuravan.smartsupply.pojo.ItemInfo
import com.asuravan.smartsupply.pojo.Order
import com.asuravan.smartsupply.pojo.OrderBundle
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface OrderBundleService {
    @POST("/order/placeorder")
    fun placeOrder(@Body bundle: OrderBundle): Call<List<Order>>

    @POST("/order/accept")
    fun accept(@Body bundle: OrderBundle): Call<List<Order>>

    @POST("/order/reject")
    fun reject(@Body bundle: OrderBundle): Call<List<Order>>

    @POST("/order/acceptdelivery")
    fun acceptdelivery(@Body bundle: OrderBundle): Call<List<Order>>

    @POST("/order/rejectdelivery")
    fun rejectdelivery(@Body bundle: OrderBundle): Call<List<Order>>

}