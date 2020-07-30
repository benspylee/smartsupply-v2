package com.bluebee.smartsupply.model;

import java.io.Serializable;
import java.sql.*;

public class OrderPayment implements Serializable {

    private Integer orderpaymentid;
    private Integer orderid;
    private Integer totalamt;
    private Integer paymode;
    private Integer ispaid;
    private Integer status;
     
     public Integer getOrderpaymentid(){ 
    return this.orderpaymentid;
    }
    public void setOrderpaymentid(Integer orderpaymentid){ 
    this.orderpaymentid=orderpaymentid;
    }
    public Integer getOrderid(){ 
    return this.orderid;
    }
    public void setOrderid(Integer orderid){ 
    this.orderid=orderid;
    }
    public Integer getTotalamt(){ 
    return this.totalamt;
    }
    public void setTotalamt(Integer totalamt){ 
    this.totalamt=totalamt;
    }
    public Integer getPaymode(){ 
    return this.paymode;
    }
    public void setPaymode(Integer paymode){ 
    this.paymode=paymode;
    }
    public Integer getIspaid(){ 
    return this.ispaid;
    }
    public void setIspaid(Integer ispaid){ 
    this.ispaid=ispaid;
    }
    public Integer getStatus(){ 
    return this.status;
    }
    public void setStatus(Integer status){ 
    this.status=status;
    }


}