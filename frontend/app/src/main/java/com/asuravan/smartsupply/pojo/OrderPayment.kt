package com.asuravan.smartsupply.pojo

import android.os.Parcel
import android.os.Parcelable

class OrderPayment():Parcelable {

    var orderpaymentid: Int? = null
    var orderid: Int? = null
    var totalamt: Int? = null
    var paymode: Int? = null
    var ispaid: Int? = null
    var status: Int? = null

    constructor(parcel: Parcel) : this() {
        orderpaymentid = parcel.readValue(Int::class.java.classLoader) as? Int
        orderid = parcel.readValue(Int::class.java.classLoader) as? Int
        totalamt = parcel.readValue(Int::class.java.classLoader) as? Int
        paymode = parcel.readValue(Int::class.java.classLoader) as? Int
        ispaid = parcel.readValue(Int::class.java.classLoader) as? Int
        status = parcel.readValue(Int::class.java.classLoader) as? Int
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(orderpaymentid)
        parcel.writeValue(orderid)
        parcel.writeValue(totalamt)
        parcel.writeValue(paymode)
        parcel.writeValue(ispaid)
        parcel.writeValue(status)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrderPayment> {
        override fun createFromParcel(parcel: Parcel): OrderPayment {
            return OrderPayment(parcel)
        }

        override fun newArray(size: Int): Array<OrderPayment?> {
            return arrayOfNulls(size)
        }
    }
}