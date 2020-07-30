package com.asuravan.smartsupply.service

import com.asuravan.smartsupply.pojo.ItemInfo
import com.asuravan.smartsupply.pojo.Response
import retrofit2.Call
import retrofit2.http.*

interface ItemService {
    @GET("/items")
    fun getAllItems(): Call<List<ItemInfo>>
    @GET("/items/{id}")
    fun getItemById(@Path("id") itemid:Int)
    @POST("/items")
    fun addItem(@Body itemInfo: ItemInfo): Call<ItemInfo>
    @PUT("/items")
    fun updateItem(@Body itemInfo: ItemInfo): Call<ItemInfo>
    @DELETE("/items/{id}")
    fun deleteItemById(@Path("id") itemid:Int): Call<ItemInfo>
    @GET("/items/users/{appuserid}")
    fun getItemsByUserId(@Path("appuserid") appuserid:Int): Call<List<ItemInfo>>
}