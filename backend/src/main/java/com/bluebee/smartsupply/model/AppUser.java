package com.bluebee.smartsupply.model;

import java.io.Serializable;

public class AppUser implements Serializable {

    private Integer appusercd;
    private String firstname;
    private String lastname;
    private String mobileno;
    private String emailid;
    private String password;
    private Integer appuserrolecd;
    private Integer status;
     
     public Integer getAppusercd(){ 
    return this.appusercd;
    }
    public void setAppusercd(Integer appusercd){ 
    this.appusercd=appusercd;
    }
    public String getFirstname(){ 
    return this.firstname;
    }
    public void setFirstname(String firstname){ 
    this.firstname=firstname;
    }
    public String getLastname(){ 
    return this.lastname;
    }
    public void setLastname(String lastname){ 
    this.lastname=lastname;
    }
    public String getMobileno(){ 
    return this.mobileno;
    }
    public void setMobileno(String mobileno){ 
    this.mobileno=mobileno;
    }
    public String getEmailid(){ 
    return this.emailid;
    }
    public void setEmailid(String emailid){ 
    this.emailid=emailid;
    }
    public String getPassword(){ 
    return this.password;
    }
    public void setPassword(String password){ 
    this.password=password;
    }
    public Integer getAppuserrolecd(){ 
    return this.appuserrolecd;
    }
    public void setAppuserrolecd(Integer appuserrolecd){ 
    this.appuserrolecd=appuserrolecd;
    }
    public Integer getStatus(){ 
    return this.status;
    }
    public void setStatus(Integer status){ 
    this.status=status;
    }


}