package com.bluebee.smartsupply.controller;

import com.bluebee.smartsupply.model.OrderPayment;
import com.bluebee.smartsupply.model.Result;
import com.bluebee.smartsupply.service.OrderPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderPaymentController  {

    @Autowired
    OrderPaymentService orderpaymentService;

    @RequestMapping(value = "orderpayment",method = RequestMethod.GET)
    public List<OrderPayment> getAllOrderPayment() throws Exception{
        return orderpaymentService.getAllOrderPayment();
    }

    @RequestMapping(value = "orderpayment/{orderpaymentid}",method = RequestMethod.GET)
    public OrderPayment getOrderPaymentById(@PathVariable("orderpaymentid")int orderpaymentid)  throws Exception{
        return orderpaymentService.getOrderPaymentById(orderpaymentid);
    }

    @RequestMapping(value = "orderpayment",method = RequestMethod.POST)
    public OrderPayment addOrderPayment(@RequestBody OrderPayment orderpayment)  throws Exception{
        return orderpaymentService.addOrderPayment(orderpayment);
    }

    @RequestMapping(value = "orderpayment",method = RequestMethod.PUT)
    public OrderPayment updateOrderPayment(@RequestBody OrderPayment orderpayment)  throws Exception{
        return orderpaymentService.updateOrderPayment(orderpayment);
    }

    @RequestMapping(value = "orderpayment/{orderpaymentid}",method = RequestMethod.DELETE)
    public OrderPayment deleteOrderPayment(@PathVariable("orderpaymentid")int orderpaymentid)  throws Exception{
        return orderpaymentService.deleteOrderPayment(orderpaymentid);
    }

    @RequestMapping(value = "orderpayment/template",method = RequestMethod.GET)
        public OrderPayment templateOrderPayment()  throws Exception{
            return new OrderPayment();
        }

}