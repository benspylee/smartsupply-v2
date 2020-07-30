package com.bluebee.smartsupply.model;

import java.io.Serializable;

public class Unit implements Serializable {

    private Integer unitcode;
    private String unitname;
    private String unitdesc;
    private Integer status;
     
     public Integer getUnitcode(){ 
    return this.unitcode;
    }
    public void setUnitcode(Integer unitcode){ 
    this.unitcode=unitcode;
    }
    public String getUnitname(){ 
    return this.unitname;
    }
    public void setUnitname(String unitname){ 
    this.unitname=unitname;
    }
    public String getUnitdesc(){ 
    return this.unitdesc;
    }
    public void setUnitdesc(String unitdesc){ 
    this.unitdesc=unitdesc;
    }
    public Integer getStatus(){ 
    return this.status;
    }
    public void setStatus(Integer status){ 
    this.status=status;
    }


}