package com.asuravan.smartsupply.pojo;

import java.io.Serializable;

public class AppPushMessage  implements Serializable {
    private int appusercd;
    private String deliveryaddress;

    public int getAppusercd() {
        return appusercd;
    }

    public void setAppusercd(int appusercd) {
        this.appusercd = appusercd;
    }

    public String getDeliveryaddress() {
        return deliveryaddress;
    }

    public void setDeliveryaddress(String deliveryaddress) {
        this.deliveryaddress = deliveryaddress;
    }
}
