package com.asuravan.smartsupply.pojo

class Response<T> {
    var statuscode:Int = 0
    var body: T? = null;
    var message:String = "";
}