package com.bluebee.smartsupply.model;

import java.io.Serializable;
import java.sql.*;

public class DeliveryTask implements Serializable {

    private Integer deliverytaskid;
    private Integer orderid;
    private Integer appusercd;
    private Integer opentask;
    private Integer acceptstatcd;
    private Integer status;
     
     public Integer getDeliverytaskid(){ 
    return this.deliverytaskid;
    }
    public void setDeliverytaskid(Integer deliverytaskid){ 
    this.deliverytaskid=deliverytaskid;
    }
    public Integer getOrderid(){ 
    return this.orderid;
    }
    public void setOrderid(Integer orderid){ 
    this.orderid=orderid;
    }
    public Integer getAppusercd(){ 
    return this.appusercd;
    }
    public void setAppusercd(Integer appusercd){ 
    this.appusercd=appusercd;
    }
    public Integer getOpentask(){ 
    return this.opentask;
    }
    public void setOpentask(Integer opentask){ 
    this.opentask=opentask;
    }
    public Integer getAcceptstatcd(){ 
    return this.acceptstatcd;
    }
    public void setAcceptstatcd(Integer acceptstatcd){ 
    this.acceptstatcd=acceptstatcd;
    }
    public Integer getStatus(){ 
    return this.status;
    }
    public void setStatus(Integer status){ 
    this.status=status;
    }


}