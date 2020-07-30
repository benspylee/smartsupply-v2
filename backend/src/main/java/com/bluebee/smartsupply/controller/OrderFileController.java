package com.bluebee.smartsupply.controller;

import com.bluebee.smartsupply.model.OrderFile;
import com.bluebee.smartsupply.model.Result;
import com.bluebee.smartsupply.service.OrderFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderFileController  {

    @Autowired
    OrderFileService orderfileService;

    @RequestMapping(value = "orderfile",method = RequestMethod.GET)
    public List<OrderFile> getAllOrderFile() throws Exception{
        return orderfileService.getAllOrderFile();
    }

    @RequestMapping(value = "orderfile/{orderfileid}",method = RequestMethod.GET)
    public OrderFile getOrderFileById(@PathVariable("orderfileid")int orderfileid)  throws Exception{
        return orderfileService.getOrderFileById(orderfileid);
    }

    @RequestMapping(value = "orderfile",method = RequestMethod.POST)
    public OrderFile addOrderFile(@RequestBody OrderFile orderfile)  throws Exception{
        return orderfileService.addOrderFile(orderfile);
    }

    @RequestMapping(value = "orderfile",method = RequestMethod.PUT)
    public OrderFile updateOrderFile(@RequestBody OrderFile orderfile)  throws Exception{
        return orderfileService.updateOrderFile(orderfile);
    }

    @RequestMapping(value = "orderfile/{orderfileid}",method = RequestMethod.DELETE)
    public OrderFile deleteOrderFile(@PathVariable("orderfileid")int orderfileid)  throws Exception{
        return orderfileService.deleteOrderFile(orderfileid);
    }

    @RequestMapping(value = "orderfile/template",method = RequestMethod.GET)
        public OrderFile templateOrderFile()  throws Exception{
            return new OrderFile();
        }

}