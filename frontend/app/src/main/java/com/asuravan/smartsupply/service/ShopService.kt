package com.asuravan.smartsupply.service

import com.asuravan.smartsupply.pojo.Shop
import com.asuravan.smartsupply.pojo.Response
import retrofit2.Call
import retrofit2.http.*

interface ShopService {
    @GET("/shop")
    fun getAllshop(): Call<List<Shop>>
    @GET("/shop/{id}")
    fun getShopById(@Path("id") shopid:Int)
    @POST("/shop")
    fun addShop(@Body shop: Shop): Call<Shop>
    @PUT("/shop")
    fun updateShop(@Body shop: Shop): Call<Shop>
    @DELETE("/shop/{id}")
    fun deleteShopById(@Path("id") shopid:Int): Call<Shop>
    @GET("/shop/search")
    fun getShopbyCriteria(@Query("zipcode")  zipcode:String, @Query("shopname")  shopname:String, @Query("itemname")  itemname:String): Call<List<Shop>>
    @GET("shop/users/{appuserid}")
    fun getShopByUserId(@Path("appuserid") appuserid:Int): Call<List<Shop>>
}