package com.bluebee.smartsupply.controller;

import com.bluebee.smartsupply.model.Shop;
import com.bluebee.smartsupply.model.Result;
import com.bluebee.smartsupply.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ShopController  {

    @Autowired
    ShopService shopService;

    @RequestMapping(value = "shop",method = RequestMethod.GET)
    public List<Shop> getAllShop() throws Exception{
        return shopService.getAllShop();
    }

    @RequestMapping(value = "shop/{shop_cd}",method = RequestMethod.GET)
    public Shop getShopById(@PathVariable("shop_cd")int shop_cd)  throws Exception{
        return shopService.getShopById(shop_cd);
    }

    @RequestMapping(value = "shop",method = RequestMethod.POST)
    public Shop addShop(@RequestBody Shop shop)  throws Exception{
        return shopService.addShop(shop);
    }

    @RequestMapping(value = "shop",method = RequestMethod.PUT)
    public Shop updateShop(@RequestBody Shop shop)  throws Exception{
        return shopService.updateShop(shop);
    }

    @RequestMapping(value = "shop/{shop_cd}",method = RequestMethod.DELETE)
    public Shop deleteShop(@PathVariable("shop_cd")int shop_cd)  throws Exception{
        return shopService.deleteShop(shop_cd);
    }

    @RequestMapping(value = "shop/template",method = RequestMethod.GET)
        public Shop templateShop()  throws Exception{
            return new Shop();
        }

}