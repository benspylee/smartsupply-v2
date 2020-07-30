package com.bluebee.smartsupply.controller;

import com.bluebee.smartsupply.model.DeliveryTask;
import com.bluebee.smartsupply.model.Result;
import com.bluebee.smartsupply.service.DeliveryTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DeliveryTaskController  {

    @Autowired
    DeliveryTaskService deliverytaskService;

    @RequestMapping(value = "deliverytask",method = RequestMethod.GET)
    public List<DeliveryTask> getAllDeliveryTask() throws Exception{
        return deliverytaskService.getAllDeliveryTask();
    }

    @RequestMapping(value = "deliverytask/{deliverytaskid}",method = RequestMethod.GET)
    public DeliveryTask getDeliveryTaskById(@PathVariable("deliverytaskid")int deliverytaskid)  throws Exception{
        return deliverytaskService.getDeliveryTaskById(deliverytaskid);
    }

    @RequestMapping(value = "deliverytask",method = RequestMethod.POST)
    public DeliveryTask addDeliveryTask(@RequestBody DeliveryTask deliverytask)  throws Exception{
        return deliverytaskService.addDeliveryTask(deliverytask);
    }

    @RequestMapping(value = "deliverytask",method = RequestMethod.PUT)
    public DeliveryTask updateDeliveryTask(@RequestBody DeliveryTask deliverytask)  throws Exception{
        return deliverytaskService.updateDeliveryTask(deliverytask);
    }

    @RequestMapping(value = "deliverytask/{deliverytaskid}",method = RequestMethod.DELETE)
    public DeliveryTask deleteDeliveryTask(@PathVariable("deliverytaskid")int deliverytaskid)  throws Exception{
        return deliverytaskService.deleteDeliveryTask(deliverytaskid);
    }

    @RequestMapping(value = "deliverytask/template",method = RequestMethod.GET)
        public DeliveryTask templateDeliveryTask()  throws Exception{
            return new DeliveryTask();
        }

}