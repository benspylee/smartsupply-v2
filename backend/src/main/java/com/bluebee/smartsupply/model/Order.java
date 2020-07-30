package com.bluebee.smartsupply.model;

import java.io.Serializable;
import java.sql.*;

public class Order implements Serializable {

    private Integer orderid;
    private Integer orderby;
    private Integer orderto;
    private Integer shopcd;
    private Timestamp orderdt;
    private Integer status;

    private String totOrderPrice;
    private Integer orderItmcnt;
    private String orderStatDesc;
    private String customerName;
    private String deliveryAddress;
    private String shopAddress;
    private String shopName;

    private Integer taskId;
    private Integer driverUserid;
    private Integer openTask;
    private Integer acceptstatcd;
    private Timestamp pickupts;
    private Integer deliverytype;
    private String driverDetails;

    public Timestamp getPickupts() {
        return pickupts;
    }

    public void setPickupts(Timestamp pickupts) {
        this.pickupts = pickupts;
    }

    public Integer getDeliverytype() {
        return deliverytype;
    }

    public void setDeliverytype(Integer deliverytype) {
        this.deliverytype = deliverytype;
    }

    public String getDriverDetails() {
        return driverDetails;
    }

    public void setDriverDetails(String driverDetails) {
        this.driverDetails = driverDetails;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getDriverUserid() {
        return driverUserid;
    }

    public void setDriverUserid(Integer driverUserid) {
        this.driverUserid = driverUserid;
    }

    public Integer getOpenTask() {
        return openTask;
    }

    public void setOpenTask(Integer openTask) {
        this.openTask = openTask;
    }

    public Integer getAcceptstatcd() {
        return acceptstatcd;
    }

    public void setAcceptstatcd(Integer acceptstatcd) {
        this.acceptstatcd = acceptstatcd;
    }

    public String getTotOrderPrice() {
        return totOrderPrice;
    }

    public void setTotOrderPrice(String totOrderPrice) {
        this.totOrderPrice = totOrderPrice;
    }

    public Integer getOrderItmcnt() {
        return orderItmcnt;
    }

    public void setOrderItmcnt(Integer orderItmcnt) {
        this.orderItmcnt = orderItmcnt;
    }

    public String getOrderStatDesc() {
        return orderStatDesc;
    }

    public void setOrderStatDesc(String orderStatDesc) {
        this.orderStatDesc = orderStatDesc;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Integer getOrderid(){
    return this.orderid;
    }
    public void setOrderid(Integer orderid){ 
    this.orderid=orderid;
    }
    public Integer getOrderby(){ 
    return this.orderby;
    }
    public void setOrderby(Integer orderby){ 
    this.orderby=orderby;
    }
    public Integer getOrderto(){ 
    return this.orderto;
    }
    public void setOrderto(Integer orderto){ 
    this.orderto=orderto;
    }
    public Integer getShopcd(){ 
    return this.shopcd;
    }
    public void setShopcd(Integer shopcd){ 
    this.shopcd=shopcd;
    }
    public Timestamp getOrderdt(){ 
    return this.orderdt;
    }
    public void setOrderdt(Timestamp orderdt){ 
    this.orderdt=orderdt;
    }
    public Integer getStatus(){ 
    return this.status;
    }
    public void setStatus(Integer status){ 
    this.status=status;
    }


}