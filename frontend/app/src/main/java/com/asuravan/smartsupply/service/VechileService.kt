package com.asuravan.smartsupply.service

import com.asuravan.smartsupply.pojo.AppUser
import com.asuravan.smartsupply.pojo.ItemInfo
import com.asuravan.smartsupply.pojo.Vechile
import retrofit2.Call
import retrofit2.http.*

interface VechileService {
    @GET("/vechile")
    fun getAllVechile(): Call<List<Vechile>>
    @GET("/vechile/{id}")
    fun getVechileById(@Path("id") vechileid:Int)
    @POST("/vechile")
    fun addVechile(@Body vechile: Vechile): Call<Vechile>
    @PUT("/vechile")
    fun updateVechile(@Body vechile: Vechile): Call<Vechile>
    @DELETE("/vechile/{id}")
    fun deleteVechileById(@Path("id") vechileid:Int): Call<Vechile>
}