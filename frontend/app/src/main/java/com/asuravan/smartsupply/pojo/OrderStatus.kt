package com.asuravan.smartsupply.pojo

import android.os.Parcel
import android.os.Parcelable
import java.sql.Timestamp

class OrderStatus() :Parcelable {

   var orderstatusid: Int? = null
   var orderid: Int? = null
   var orderstatuscd: Int? = null
   var orderts: Timestamp? = null
   var updateuid: Int? = null

    constructor(parcel: Parcel) : this() {
        orderstatusid = parcel.readValue(Int::class.java.classLoader) as? Int
        orderid = parcel.readValue(Int::class.java.classLoader) as? Int
        orderstatuscd = parcel.readValue(Int::class.java.classLoader) as? Int
        updateuid = parcel.readValue(Int::class.java.classLoader) as? Int
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(orderstatusid)
        parcel.writeValue(orderid)
        parcel.writeValue(orderstatuscd)
        parcel.writeValue(updateuid)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrderStatus> {
        override fun createFromParcel(parcel: Parcel): OrderStatus {
            return OrderStatus(parcel)
        }

        override fun newArray(size: Int): Array<OrderStatus?> {
            return arrayOfNulls(size)
        }
    }


}