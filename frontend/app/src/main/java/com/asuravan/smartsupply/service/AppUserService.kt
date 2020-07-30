package com.asuravan.smartsupply.service

import com.asuravan.smartsupply.pojo.AppUser
import com.asuravan.smartsupply.pojo.ItemInfo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AppUserService {
    @POST("/appuser/login")
    fun logIn(@Body itemInfo: AppUser): Call<AppUser>
    @POST("/appuser")
    fun addAppUser(@Body itemInfo: AppUser): Call<AppUser>
}