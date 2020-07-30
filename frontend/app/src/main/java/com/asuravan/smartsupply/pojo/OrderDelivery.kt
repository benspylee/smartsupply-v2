package com.asuravan.smartsupply.pojo

import android.os.Parcel
import android.os.Parcelable
import java.sql.Timestamp

class OrderDelivery ():Parcelable{

    var orderdeliveryid: Int? = null
    var orderid: Int? = null
    var deliverytype: Int? = null
    var vechilecd: Int? = null
    var driveruserid: Int? = null
    var fromaddr: Int? = null
    var deliveryaddr: Int? = null
    var pickupts: Timestamp? = null
    var status: Int? = null

    constructor(parcel: Parcel) : this() {
        orderdeliveryid = parcel.readValue(Int::class.java.classLoader) as? Int
        orderid = parcel.readValue(Int::class.java.classLoader) as? Int
        deliverytype = parcel.readValue(Int::class.java.classLoader) as? Int
        vechilecd = parcel.readValue(Int::class.java.classLoader) as? Int
        driveruserid = parcel.readValue(Int::class.java.classLoader) as? Int
        fromaddr = parcel.readValue(Int::class.java.classLoader) as? Int
        deliveryaddr = parcel.readValue(Int::class.java.classLoader) as? Int
        status = parcel.readValue(Int::class.java.classLoader) as? Int
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(orderdeliveryid)
        parcel.writeValue(orderid)
        parcel.writeValue(deliverytype)
        parcel.writeValue(vechilecd)
        parcel.writeValue(driveruserid)
        parcel.writeValue(fromaddr)
        parcel.writeValue(deliveryaddr)
        parcel.writeValue(status)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrderDelivery> {
        override fun createFromParcel(parcel: Parcel): OrderDelivery {
            return OrderDelivery(parcel)
        }

        override fun newArray(size: Int): Array<OrderDelivery?> {
            return arrayOfNulls(size)
        }
    }
}