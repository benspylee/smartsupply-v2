package com.asuravan.smartsupply.service

import com.asuravan.smartsupply.pojo.AppUser
import com.asuravan.smartsupply.pojo.ItemInfo
import com.asuravan.smartsupply.pojo.AppUserAddress
import retrofit2.Call
import retrofit2.http.*

interface AppUserAddressService {
    @GET("/appuseraddress")
    fun getAllAppUserAddress(): Call<List<AppUserAddress>>
    @GET("/appuseraddress/{id}")
    fun getAppUserAddressById(@Path("id") addressid:Int)
    @POST("/appuseraddress")
    fun addAppUserAddress(@Body appuseraddress: AppUserAddress): Call<AppUserAddress>
    @PUT("/appuseraddress")
    fun updateAppUserAddress(@Body appuseraddress: AppUserAddress): Call<AppUserAddress>
    @DELETE("/appuseraddress/{id}")
    fun deleteAppUserAddressById(@Path("id") addressid:Int): Call<AppUserAddress>
    @GET("appuseraddress/users/{appuserid}")
    fun getAppUserAddressByUserId(@Path("appuserid") appuserid:Int): Call<List<AppUserAddress>>
}