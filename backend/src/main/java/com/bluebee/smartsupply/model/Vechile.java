package com.bluebee.smartsupply.model;

import java.io.Serializable;

public class Vechile implements Serializable {

    private Integer vechilecd;
    private String vechileregno;
    private String ownername;
    private String addess1;
    private String address2;
    private String address3;
    private String zipcode;
    private String mobileno;
    private Integer appusercd;
    private Integer status;
     
     public Integer getVechilecd(){ 
    return this.vechilecd;
    }
    public void setVechilecd(Integer vechilecd){ 
    this.vechilecd=vechilecd;
    }
    public String getVechileregno(){ 
    return this.vechileregno;
    }
    public void setVechileregno(String vechileregno){ 
    this.vechileregno=vechileregno;
    }
    public String getOwnername(){ 
    return this.ownername;
    }
    public void setOwnername(String ownername){ 
    this.ownername=ownername;
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
    public String getMobileno(){ 
    return this.mobileno;
    }
    public void setMobileno(String mobileno){ 
    this.mobileno=mobileno;
    }
    public Integer getAppusercd(){ 
    return this.appusercd;
    }
    public void setAppusercd(Integer appusercd){ 
    this.appusercd=appusercd;
    }
    public Integer getStatus(){ 
    return this.status;
    }
    public void setStatus(Integer status){ 
    this.status=status;
    }


}