package com.asuravan.smartsupply.service

import com.asuravan.smartsupply.pojo.AppUser
import com.asuravan.smartsupply.pojo.ItemInfo
import com.asuravan.smartsupply.pojo.Order
import com.asuravan.smartsupply.pojo.OrderBundle
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface OrderService {
    @GET("order/customer/{appusercd}")
    fun getOrderByCustomer(@Path("appusercd") appusercd:Int): Call<List<Order>>
    @GET("order/shopowner/{appusercd}")
    fun getOrderByShopOwner(@Path("appusercd") appusercd:Int): Call<List<Order>>
    @GET("order/transporter/{appusercd}")
    fun getOrderByTransporter(@Path("appusercd") appusercd:Int): Call<List<Order>>

}