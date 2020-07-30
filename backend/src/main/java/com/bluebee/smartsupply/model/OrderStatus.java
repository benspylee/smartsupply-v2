package com.bluebee.smartsupply.model;

import java.io.Serializable;
import java.sql.*;

public class OrderStatus implements Serializable {

    private Integer orderstatusid;
    private Integer orderid;
    private Integer orderstatuscd;
    private Timestamp orderts;
    private Integer updateuid;

    public Integer getUpdateuid() {
        return updateuid;
    }

    public void setUpdateuid(Integer updateuid) {
        this.updateuid = updateuid;
    }

    public Integer getOrderstatusid(){
    return this.orderstatusid;
    }
    public void setOrderstatusid(Integer orderstatusid){ 
    this.orderstatusid=orderstatusid;
    }
    public Integer getOrderid(){ 
    return this.orderid;
    }
    public void setOrderid(Integer orderid){ 
    this.orderid=orderid;
    }
    public Integer getOrderstatuscd(){ 
    return this.orderstatuscd;
    }
    public void setOrderstatuscd(Integer orderstatuscd){ 
    this.orderstatuscd=orderstatuscd;
    }
    public Timestamp getOrderts(){ 
    return this.orderts;
    }
    public void setOrderts(Timestamp orderts){ 
    this.orderts=orderts;
    }

}