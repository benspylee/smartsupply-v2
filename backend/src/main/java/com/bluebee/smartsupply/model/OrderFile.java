package com.bluebee.smartsupply.model;

import java.io.Serializable;
import java.sql.*;

public class OrderFile implements Serializable {

    private Integer orderfileid;
    private Integer orderid;
    private String invoicefile;
    private Integer infectreport;
    private Integer status;
     
     public Integer getOrderfileid(){ 
    return this.orderfileid;
    }
    public void setOrderfileid(Integer orderfileid){ 
    this.orderfileid=orderfileid;
    }
    public Integer getOrderid(){ 
    return this.orderid;
    }
    public void setOrderid(Integer orderid){ 
    this.orderid=orderid;
    }
    public String getInvoicefile(){ 
    return this.invoicefile;
    }
    public void setInvoicefile(String invoicefile){ 
    this.invoicefile=invoicefile;
    }
    public Integer getInfectreport(){ 
    return this.infectreport;
    }
    public void setInfectreport(Integer infectreport){ 
    this.infectreport=infectreport;
    }
    public Integer getStatus(){ 
    return this.status;
    }
    public void setStatus(Integer status){ 
    this.status=status;
    }


}