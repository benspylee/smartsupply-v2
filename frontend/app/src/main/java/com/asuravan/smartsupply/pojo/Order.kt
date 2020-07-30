package com.asuravan.smartsupply.pojo

import android.os.Parcel
import android.os.Parcelable
import java.sql.Timestamp

class Order():Parcelable {
    var orderid: Int? = null
    var orderby: Int? = null
    var orderto: Int? = null
    var shopcd: Int? = null
    var orderdt: Timestamp? = null
    var status: Int? = null

   var totOrderPrice: String? = null
   var orderItmcnt: Int? = null
   var orderStatDesc: String? = null
   var customerName: String? = null
   var deliveryAddress: String? = null
   var shopAddress: String? = null
   var shopName: String? = null

   var taskId: Int? = null
   var driverUserid: Int? = null
   var openTask: Int? = null
   var acceptstatcd: Int? = null

    var pickupts: Timestamp? = null
    var deliverytype: Int? = null
    var driverDetails: String? = null

    constructor(parcel: Parcel) : this() {
        orderid = parcel.readValue(Int::class.java.classLoader) as? Int
        orderby = parcel.readValue(Int::class.java.classLoader) as? Int
        orderto = parcel.readValue(Int::class.java.classLoader) as? Int
        shopcd = parcel.readValue(Int::class.java.classLoader) as? Int
        status = parcel.readValue(Int::class.java.classLoader) as? Int
        totOrderPrice = parcel.readString()
        orderItmcnt = parcel.readValue(Int::class.java.classLoader) as? Int
        orderStatDesc = parcel.readString()
        customerName = parcel.readString()
        deliveryAddress = parcel.readString()
        shopAddress = parcel.readString()
        shopName = parcel.readString()
        taskId = parcel.readValue(Int::class.java.classLoader) as? Int
        driverUserid = parcel.readValue(Int::class.java.classLoader) as? Int
        openTask = parcel.readValue(Int::class.java.classLoader) as? Int
        acceptstatcd = parcel.readValue(Int::class.java.classLoader) as? Int
        deliverytype = parcel.readValue(Int::class.java.classLoader) as? Int
        driverDetails = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(orderid)
        parcel.writeValue(orderby)
        parcel.writeValue(orderto)
        parcel.writeValue(shopcd)
        parcel.writeValue(status)
        parcel.writeString(totOrderPrice)
        parcel.writeValue(orderItmcnt)
        parcel.writeString(orderStatDesc)
        parcel.writeString(customerName)
        parcel.writeString(deliveryAddress)
        parcel.writeString(shopAddress)
        parcel.writeString(shopName)
        parcel.writeValue(taskId)
        parcel.writeValue(driverUserid)
        parcel.writeValue(openTask)
        parcel.writeValue(acceptstatcd)
        parcel.writeValue(deliverytype)
        parcel.writeString(driverDetails)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Order> {
        override fun createFromParcel(parcel: Parcel): Order {
            return Order(parcel)
        }

        override fun newArray(size: Int): Array<Order?> {
            return arrayOfNulls(size)
        }
    }


}