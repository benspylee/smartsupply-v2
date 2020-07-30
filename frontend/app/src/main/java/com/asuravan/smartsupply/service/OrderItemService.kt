package com.asuravan.smartsupply.service

import com.asuravan.smartsupply.pojo.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface OrderItemService {
    @GET("/orderitem")
    fun getAllshop(): Call<List<OrderItem>>
    @GET("/shop/{id}")
    fun getOrderItemById(@Path("id") orderitemid:Int)
    @GET("/orderitem/order/{id}")
    fun getOrderItemByOrderId(@Path("id") orderitemid:Int): Call<List<OrderItem>>
    @POST("/shop")
    fun addOrderItem(@Body shop: OrderItem): Call<OrderItem>
    @PUT("/shop")
    fun updateOrderItem(@Body shop: OrderItem): Call<OrderItem>
    @DELETE("/shop/{id}")
    fun deleteOrderItemById(@Path("id") orderitemid:Int): Call<OrderItem>

    @GET("/order/generateinvoice/{id}")
    fun downloadInvoiceByOrderId(@Path("id") orderid:Int):Call<ResponseBody>
}