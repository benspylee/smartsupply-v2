package com.bluebee.smartsupply.controller;

import com.bluebee.smartsupply.model.OrderItem;
import com.bluebee.smartsupply.model.Result;
import com.bluebee.smartsupply.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderItemController  {

    @Autowired
    OrderItemService orderitemService;

    @RequestMapping(value = "orderitem",method = RequestMethod.GET)
    public List<OrderItem> getAllOrderItem() throws Exception{
        return orderitemService.getAllOrderItem();
    }

    @RequestMapping(value = "orderitem/{orderitemid}",method = RequestMethod.GET)
    public OrderItem getOrderItemById(@PathVariable("orderitemid")int orderitemid)  throws Exception{
        return orderitemService.getOrderItemById(orderitemid);
    }
    @RequestMapping(value = "orderitem/order/{orderid}",method = RequestMethod.GET)
    public List<OrderItem> getOrderItemByOrderId(@PathVariable("orderid")int orderid)  throws Exception{
        return orderitemService.getOrderItemByOrderId(orderid);
    }

    @RequestMapping(value = "orderitem",method = RequestMethod.POST)
    public OrderItem addOrderItem(@RequestBody OrderItem orderitem)  throws Exception{
        return orderitemService.addOrderItem(orderitem);
    }

    @RequestMapping(value = "orderitem",method = RequestMethod.PUT)
    public OrderItem updateOrderItem(@RequestBody OrderItem orderitem)  throws Exception{
        return orderitemService.updateOrderItem(orderitem);
    }

    @RequestMapping(value = "orderitem/{orderitemid}",method = RequestMethod.DELETE)
    public OrderItem deleteOrderItem(@PathVariable("orderitemid")int orderitemid)  throws Exception{
        return orderitemService.deleteOrderItem(orderitemid);
    }

    @RequestMapping(value = "orderitem/template",method = RequestMethod.GET)
        public OrderItem templateOrderItem()  throws Exception{
            return new OrderItem();
        }

}