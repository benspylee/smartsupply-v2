package com.asuravan.smartsupply.pojo

import android.os.Parcel
import android.os.Parcelable

class AppUserAddress() :Parcelable {
    var appuseraddrcd: Int? = null
    var appusercd: Int? = null
    var addess1: String? = null
    var address2: String? = null
    var address3: String? = null
    var zipcode: String? = null
    var status: Int? = null
    var isprimary: Int? = null

    constructor(parcel: Parcel) : this() {
        appuseraddrcd = parcel.readValue(Int::class.java.classLoader) as? Int
        appusercd = parcel.readValue(Int::class.java.classLoader) as? Int
        addess1 = parcel.readString()
        address2 = parcel.readString()
        address3 = parcel.readString()
        zipcode = parcel.readString()
        status = parcel.readValue(Int::class.java.classLoader) as? Int
        isprimary = parcel.readValue(Int::class.java.classLoader) as? Int
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(appuseraddrcd)
        parcel.writeValue(appusercd)
        parcel.writeString(addess1)
        parcel.writeString(address2)
        parcel.writeString(address3)
        parcel.writeString(zipcode)
        parcel.writeValue(status)
        parcel.writeValue(isprimary)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AppUserAddress> {
        override fun createFromParcel(parcel: Parcel): AppUserAddress {
            return AppUserAddress(parcel)
        }

        override fun newArray(size: Int): Array<AppUserAddress?> {
            return arrayOfNulls(size)
        }
    }
}