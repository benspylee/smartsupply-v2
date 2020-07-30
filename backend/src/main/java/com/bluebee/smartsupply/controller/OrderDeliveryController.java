package com.bluebee.smartsupply.controller;

import com.bluebee.smartsupply.model.OrderDelivery;
import com.bluebee.smartsupply.model.Result;
import com.bluebee.smartsupply.service.OrderDeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderDeliveryController  {

    @Autowired
    OrderDeliveryService orderdeliveryService;

    @RequestMapping(value = "orderdelivery",method = RequestMethod.GET)
    public List<OrderDelivery> getAllOrderDelivery() throws Exception{
        return orderdeliveryService.getAllOrderDelivery();
    }

    @RequestMapping(value = "orderdelivery/{orderdeliveryid}",method = RequestMethod.GET)
    public OrderDelivery getOrderDeliveryById(@PathVariable("orderdeliveryid")int orderdeliveryid)  throws Exception{
        return orderdeliveryService.getOrderDeliveryById(orderdeliveryid);
    }

    @RequestMapping(value = "orderdelivery",method = RequestMethod.POST)
    public OrderDelivery addOrderDelivery(@RequestBody OrderDelivery orderdelivery)  throws Exception{
        return orderdeliveryService.addOrderDelivery(orderdelivery);
    }

    @RequestMapping(value = "orderdelivery",method = RequestMethod.PUT)
    public OrderDelivery updateOrderDelivery(@RequestBody OrderDelivery orderdelivery)  throws Exception{
        return orderdeliveryService.updateOrderDelivery(orderdelivery);
    }

    @RequestMapping(value = "orderdelivery/{orderdeliveryid}",method = RequestMethod.DELETE)
    public OrderDelivery deleteOrderDelivery(@PathVariable("orderdeliveryid")int orderdeliveryid)  throws Exception{
        return orderdeliveryService.deleteOrderDelivery(orderdeliveryid);
    }

    @RequestMapping(value = "orderdelivery/template",method = RequestMethod.GET)
        public OrderDelivery templateOrderDelivery()  throws Exception{
            return new OrderDelivery();
        }

}