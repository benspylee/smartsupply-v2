package com.bluebee.smartsupply.controller;

import com.bluebee.smartsupply.model.Items;
import com.bluebee.smartsupply.model.Result;
import com.bluebee.smartsupply.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemController  {

    @Autowired
    ItemService itemService;

    @RequestMapping(value = "items",method = RequestMethod.GET)
    public List<Items> getAllItems() throws Exception{
        return itemService.getAllItems();
    }

    @RequestMapping(value = "items/{itemid}",method = RequestMethod.GET)
    public Items getItemById(@PathVariable("itemid")int itemid)  throws Exception{
        return itemService.getItemById(itemid);
    }

    @RequestMapping(value = "items",method = RequestMethod.POST)
    public Items addItem(@RequestBody Items item)  throws Exception{
        return itemService.addItem(item);
    }

    @RequestMapping(value = "items",method = RequestMethod.PUT)
    public Items updateItem(@RequestBody Items item)  throws Exception{
        return itemService.updateItem(item);
    }

    @RequestMapping(value = "items/{itemcode}",method = RequestMethod.DELETE)
    public Items deleteItem(@PathVariable("itemcode")int itemid)  throws Exception{
        return itemService.deleteItem(itemid);
    }
    @RequestMapping(value = "items/template",method = RequestMethod.GET)
    public Items templateItem()  throws Exception{
        return new Items();
    }


}
