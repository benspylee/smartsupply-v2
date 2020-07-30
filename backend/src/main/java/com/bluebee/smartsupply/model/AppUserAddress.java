package com.bluebee.smartsupply.model;

import java.io.Serializable;

public class AppUserAddress implements Serializable {

    private Integer appuseraddrcd;
    private Integer appusercd;
    private String addess1;
    private String address2;
    private String address3;
    private String zipcode;
    private Integer status;
    private Integer isprimary;
     
     public Integer getAppuseraddrcd(){ 
    return this.appuseraddrcd;
    }
    public void setAppuseraddrcd(Integer appuseraddrcd){ 
    this.appuseraddrcd=appuseraddrcd;
    }
    public Integer getAppusercd(){ 
    return this.appusercd;
    }
    public void setAppusercd(Integer appusercd){ 
    this.appusercd=appusercd;
    }
    public String getAddess1(){ 
    return this.addess1;
    }
    public void setAddess1(String addess1){ 
    this.addess1=addess1;
    }
    public String getAddress2(){ 
    return this.address2;
    }
    public void setAddress2(String address2){ 
    this.address2=address2;
    }
    public String getAddress3(){ 
    return this.address3;
    }
    public void setAddress3(String address3){ 
    this.address3=address3;
    }
    public String getZipcode(){ 
    return this.zipcode;
    }
    public void setZipcode(String zipcode){ 
    this.zipcode=zipcode;
    }
    public Integer getStatus(){ 
    return this.status;
    }
    public void setStatus(Integer status){ 
    this.status=status;
    }
    public Integer getIsprimary(){ 
    return this.isprimary;
    }
    public void setIsprimary(Integer isprimary){ 
    this.isprimary=isprimary;
    }


}