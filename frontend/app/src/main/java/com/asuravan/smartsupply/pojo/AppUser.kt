package com.asuravan.smartsupply.pojo

import android.os.Parcel
import android.os.Parcelable

class AppUser(): Parcelable {
    var appusercd: Int? = null
    var firstname: String? = null
    var lastname: String? = null
    var mobileno: String? = null
    var emailid: String? = null
    var password: String? = null
    var appuserrolecd: Int? = null
    var status: Int? = null

    constructor(parcel: Parcel) : this() {
        appusercd = parcel.readValue(Int::class.java.classLoader) as? Int
        firstname = parcel.readString()
        lastname = parcel.readString()
        mobileno = parcel.readString()
        emailid = parcel.readString()
        password = parcel.readString()
        appuserrolecd = parcel.readValue(Int::class.java.classLoader) as? Int
        status = parcel.readValue(Int::class.java.classLoader) as? Int
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(appusercd)
        parcel.writeString(firstname)
        parcel.writeString(lastname)
        parcel.writeString(mobileno)
        parcel.writeString(emailid)
        parcel.writeString(password)
        parcel.writeValue(appuserrolecd)
        parcel.writeValue(status)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AppUser> {
        override fun createFromParcel(parcel: Parcel): AppUser {
            return AppUser(parcel)
        }

        override fun newArray(size: Int): Array<AppUser?> {
            return arrayOfNulls(size)
        }
    }


}