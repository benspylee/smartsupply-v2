package com.bluebee.smartsupply.model;

import java.io.Serializable;
import java.sql.*;

public class OrderDelivery implements Serializable {

    private Integer orderdeliveryid;
    private Integer orderid;
    private Integer deliverytype;
    private Integer vechilecd;
    private Integer driveruserid;
    private Integer fromaddr;
    private Integer deliveryaddr;
    private Timestamp pickupts;
    private Integer status;
     
     public Integer getOrderdeliveryid(){ 
    return this.orderdeliveryid;
    }
    public void setOrderdeliveryid(Integer orderdeliveryid){ 
    this.orderdeliveryid=orderdeliveryid;
    }
    public Integer getOrderid(){ 
    return this.orderid;
    }
    public void setOrderid(Integer orderid){ 
    this.orderid=orderid;
    }
    public Integer getDeliverytype(){ 
    return this.deliverytype;
    }
    public void setDeliverytype(Integer deliverytype){ 
    this.deliverytype=deliverytype;
    }
    public Integer getVechilecd(){ 
    return this.vechilecd;
    }
    public void setVechilecd(Integer vechilecd){ 
    this.vechilecd=vechilecd;
    }
    public Integer getDriveruserid(){ 
    return this.driveruserid;
    }
    public void setDriveruserid(Integer driveruserid){ 
    this.driveruserid=driveruserid;
    }
    public Integer getFromaddr(){ 
    return this.fromaddr;
    }
    public void setFromaddr(Integer fromaddr){ 
    this.fromaddr=fromaddr;
    }
    public Integer getDeliveryaddr(){ 
    return this.deliveryaddr;
    }
    public void setDeliveryaddr(Integer deliveryaddr){ 
    this.deliveryaddr=deliveryaddr;
    }
    public Timestamp getPickupts(){ 
    return this.pickupts;
    }
    public void setPickupts(Timestamp pickupts){ 
    this.pickupts=pickupts;
    }
    public Integer getStatus(){ 
    return this.status;
    }
    public void setStatus(Integer status){ 
    this.status=status;
    }


}