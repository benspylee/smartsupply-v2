package com.bluebee.smartsupply.model;

import java.io.Serializable;

public class AppRole implements Serializable {

    private Integer approlecd;
    private String approlename;
    private Integer status;
     
     public Integer getApprolecd(){ 
    return this.approlecd;
    }
    public void setApprolecd(Integer approlecd){ 
    this.approlecd=approlecd;
    }
    public String getApprolename(){ 
    return this.approlename;
    }
    public void setApprolename(String approlename){ 
    this.approlename=approlename;
    }
    public Integer getStatus(){ 
    return this.status;
    }
    public void setStatus(Integer status){ 
    this.status=status;
    }


}