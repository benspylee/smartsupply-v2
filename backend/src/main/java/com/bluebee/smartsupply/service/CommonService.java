package com.bluebee.smartsupply.service;

import com.bluebee.smartsupply.model.Result;

public class CommonService {

    public Result wrapResponse(Object body){
       return wrapResponse(body,0,null);
    }

    public Result wrapResponse(Object body, int statuscode, String msg){
        Result response=new Result();
        response.setBody(body);
        response.setStatuscode(statuscode==0?200:statuscode);
        response.setMessage(msg==null?"success":msg);
        return response;
    }



}
