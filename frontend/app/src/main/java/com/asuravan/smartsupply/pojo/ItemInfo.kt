package com.asuravan.smartsupply.pojo

import android.os.Parcel
import android.os.Parcelable
import com.asuravan.smartsupply.R

class ItemInfo() :Parcelable{
     var itemname:String? =null;
     var itemdesc:String? =null;
     var itemId:String? =null;
     var prodcatdesc:String? =null;
     var prodcat:Int = 0;
     var url:String? =null;
     var imageid:Int = R.drawable.vegetable;
    var itemcode:Int = 0;
    var price:Double = 0.0;
    var unitcode: Int? = null
    var appusercd: Int? = null
    var quantity: Int? = 1;
    var totalprice:String? =null;
    var shopcode: Int? = 0;

    constructor(parcel: Parcel) : this() {
        itemname = parcel.readString()
        itemdesc = parcel.readString()
        itemId = parcel.readString()
        prodcatdesc = parcel.readString()
        prodcat = parcel.readInt()
        url = parcel.readString()
        imageid = parcel.readInt()
        itemcode = parcel.readInt()
        price = parcel.readDouble()
        unitcode = parcel.readValue(Int::class.java.classLoader) as? Int
        appusercd = parcel.readValue(Int::class.java.classLoader) as? Int
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(itemname)
        parcel.writeString(itemdesc)
        parcel.writeString(itemId)
        parcel.writeString(prodcatdesc)
        parcel.writeInt(prodcat)
        parcel.writeString(url)
        parcel.writeInt(imageid)
        parcel.writeInt(itemcode)
        parcel.writeDouble(price)
        parcel.writeValue(unitcode)
        parcel.writeValue(appusercd)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ItemInfo> {
        override fun createFromParcel(parcel: Parcel): ItemInfo {
            return ItemInfo(parcel)
        }

        override fun newArray(size: Int): Array<ItemInfo?> {
            return arrayOfNulls(size)
        }
    }


}



