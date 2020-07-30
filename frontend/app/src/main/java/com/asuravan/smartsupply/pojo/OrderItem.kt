package com.asuravan.smartsupply.pojo

import android.os.Parcel
import android.os.Parcelable

class OrderItem():Parcelable{
    var  orderitemid: Int? = null
    var  orderid: Int? = null
    var  itemcd: Int? = null
    var  qnty: Int? = null
    var  unitcode: Int? = null
    var  status: Int? = null
    var  shopcode: Int? = null
    var  itemname: String? = null
    var  price = 0.0
    var header=false;

    constructor(parcel: Parcel) : this() {
        orderitemid = parcel.readValue(Int::class.java.classLoader) as? Int
        orderid = parcel.readValue(Int::class.java.classLoader) as? Int
        itemcd = parcel.readValue(Int::class.java.classLoader) as? Int
        qnty = parcel.readValue(Int::class.java.classLoader) as? Int
        unitcode = parcel.readValue(Int::class.java.classLoader) as? Int
        status = parcel.readValue(Int::class.java.classLoader) as? Int
        shopcode = parcel.readValue(Int::class.java.classLoader) as? Int
        itemname = parcel.readString()
        price = parcel.readDouble()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(orderitemid)
        parcel.writeValue(orderid)
        parcel.writeValue(itemcd)
        parcel.writeValue(qnty)
        parcel.writeValue(unitcode)
        parcel.writeValue(status)
        parcel.writeValue(shopcode)
        parcel.writeString(itemname)
        parcel.writeDouble(price)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrderItem> {
        override fun createFromParcel(parcel: Parcel): OrderItem {
            return OrderItem(parcel)
        }

        override fun newArray(size: Int): Array<OrderItem?> {
            return arrayOfNulls(size)
        }
    }


}