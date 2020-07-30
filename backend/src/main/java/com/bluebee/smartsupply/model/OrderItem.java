package com.bluebee.smartsupply.model;

import java.io.Serializable;
import java.sql.*;

public class OrderItem implements Serializable {

    private Integer orderitemid;
    private Integer orderid;
    private Integer itemcd;
    private Integer qnty;
    private Integer unitcode;
    private Integer status;
    private Integer shopcode;
    private String itemname;
    private double price;

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getShopcode() {
        return shopcode;
    }

    public void setShopcode(Integer shopcode) {
        this.shopcode = shopcode;
    }

    public Integer getOrderitemid(){
    return this.orderitemid;
    }
    public void setOrderitemid(Integer orderitemid){ 
    this.orderitemid=orderitemid;
    }
    public Integer getOrderid(){ 
    return this.orderid;
    }
    public void setOrderid(Integer orderid){ 
    this.orderid=orderid;
    }
    public Integer getItemcd(){ 
    return this.itemcd;
    }
    public void setItemcd(Integer itemcd){ 
    this.itemcd=itemcd;
    }
    public Integer getQnty(){ 
    return this.qnty;
    }
    public void setQnty(Integer qnty){ 
    this.qnty=qnty;
    }
    public Integer getUnitcode(){ 
    return this.unitcode;
    }
    public void setUnitcode(Integer unitcode){ 
    this.unitcode=unitcode;
    }
    public Integer getStatus(){ 
    return this.status;
    }
    public void setStatus(Integer status){ 
    this.status=status;
    }


}