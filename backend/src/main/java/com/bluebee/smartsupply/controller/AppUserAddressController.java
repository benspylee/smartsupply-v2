package com.bluebee.smartsupply.controller;

import com.bluebee.smartsupply.model.AppUserAddress;
import com.bluebee.smartsupply.model.Result;
import com.bluebee.smartsupply.service.AppUserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AppUserAddressController  {

    @Autowired
    AppUserAddressService appuseraddressService;

    @RequestMapping(value = "appuseraddress",method = RequestMethod.GET)
    public List<AppUserAddress> getAllAppUserAddress() throws Exception{
        return appuseraddressService.getAllAppUserAddress();
    }

    @RequestMapping(value = "appuseraddress/{appuseraddresscd}",method = RequestMethod.GET)
    public AppUserAddress getAppUserAddressById(@PathVariable("appuseraddresscd")int appuseraddresscd)  throws Exception{
        return appuseraddressService.getAppUserAddressById(appuseraddresscd);
    }

    @RequestMapping(value = "appuseraddress",method = RequestMethod.POST)
    public AppUserAddress addAppUserAddress(@RequestBody AppUserAddress appuseraddress)  throws Exception{
        return appuseraddressService.addAppUserAddress(appuseraddress);
    }

    @RequestMapping(value = "appuseraddress",method = RequestMethod.PUT)
    public AppUserAddress updateAppUserAddress(@RequestBody AppUserAddress appuseraddress)  throws Exception{
        return appuseraddressService.updateAppUserAddress(appuseraddress);
    }

    @RequestMapping(value = "appuseraddress/{appuseraddresscd}",method = RequestMethod.DELETE)
    public AppUserAddress deleteAppUserAddress(@PathVariable("appuseraddresscd")int appuseraddresscd)  throws Exception{
        return appuseraddressService.deleteAppUserAddress(appuseraddresscd);
    }
    @RequestMapping(value = "appuseraddress/template",method = RequestMethod.GET)
    public AppUserAddress templateItem()  throws Exception{
        return new AppUserAddress();
    }

}