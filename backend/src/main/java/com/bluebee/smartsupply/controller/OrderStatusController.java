package com.bluebee.smartsupply.controller;

import com.bluebee.smartsupply.model.OrderStatus;
import com.bluebee.smartsupply.model.Result;
import com.bluebee.smartsupply.service.OrderStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderStatusController  {

    @Autowired
    OrderStatusService orderstatusService;

    @RequestMapping(value = "orderstatus",method = RequestMethod.GET)
    public List<OrderStatus> getAllOrderStatus() throws Exception{
        return orderstatusService.getAllOrderStatus();
    }

    @RequestMapping(value = "orderstatus/{orderstatusid}",method = RequestMethod.GET)
    public OrderStatus getOrderStatusById(@PathVariable("orderstatusid")int orderstatusid)  throws Exception{
        return orderstatusService.getOrderStatusById(orderstatusid);
    }

    @RequestMapping(value = "orderstatus",method = RequestMethod.POST)
    public OrderStatus addOrderStatus(@RequestBody OrderStatus orderstatus)  throws Exception{
        return orderstatusService.addOrderStatus(orderstatus);
    }

    @RequestMapping(value = "orderstatus",method = RequestMethod.PUT)
    public OrderStatus updateOrderStatus(@RequestBody OrderStatus orderstatus)  throws Exception{
        return orderstatusService.updateOrderStatus(orderstatus);
    }

    @RequestMapping(value = "orderstatus/{orderstatusid}",method = RequestMethod.DELETE)
    public OrderStatus deleteOrderStatus(@PathVariable("orderstatusid")int orderstatusid)  throws Exception{
        return orderstatusService.deleteOrderStatus(orderstatusid);
    }

    @RequestMapping(value = "orderstatus/template",method = RequestMethod.GET)
        public OrderStatus templateOrderStatus()  throws Exception{
            return new OrderStatus();
        }

}