package com.asuravan.smartsupply.pojo

import android.os.Parcel
import android.os.Parcelable

class Vechile() :Parcelable {

     var vechilecd: Int? = null
     var vechileregno: String? = null
     var ownername: String? = null
     var addess1: String? = null
     var address2: String? = null
     var address3: String? = null
     var zipcode: String? = null
     var mobileno: String? = null
     var appusercd: Int? = null
     var status: Int? = null

    constructor(parcel: Parcel) : this() {
        vechilecd = parcel.readValue(Int::class.java.classLoader) as? Int
        vechileregno = parcel.readString()
        ownername = parcel.readString()
        addess1 = parcel.readString()
        address2 = parcel.readString()
        address3 = parcel.readString()
        zipcode = parcel.readString()
        mobileno = parcel.readString()
        appusercd = parcel.readValue(Int::class.java.classLoader) as? Int
        status = parcel.readValue(Int::class.java.classLoader) as? Int
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(vechilecd)
        parcel.writeString(vechileregno)
        parcel.writeString(ownername)
        parcel.writeString(addess1)
        parcel.writeString(address2)
        parcel.writeString(address3)
        parcel.writeString(zipcode)
        parcel.writeString(mobileno)
        parcel.writeValue(appusercd)
        parcel.writeValue(status)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Vechile> {
        override fun createFromParcel(parcel: Parcel): Vechile {
            return Vechile(parcel)
        }

        override fun newArray(size: Int): Array<Vechile?> {
            return arrayOfNulls(size)
        }
    }


}