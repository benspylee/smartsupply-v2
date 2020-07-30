package com.bluebee.smartsupply.controller;

import com.bluebee.smartsupply.model.AppUser;
import com.bluebee.smartsupply.model.Result;
import com.bluebee.smartsupply.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AppUserController  {

    @Autowired
    AppUserService appuserService;

    @RequestMapping(value = "appuser",method = RequestMethod.GET)
    public List<AppUser> getAllAppUser() throws Exception{
        return appuserService.getAllAppUser();
    }

    @RequestMapping(value = "appuser/{appusercd}",method = RequestMethod.GET)
    public AppUser getAppUserById(@PathVariable("appusercd")int appusercd)  throws Exception{
        return appuserService.getAppUserById(appusercd);
    }

    @RequestMapping(value = "appuser",method = RequestMethod.POST)
    public AppUser addAppUser(@RequestBody AppUser appuser)  throws Exception{
        return appuserService.addAppUser(appuser);
    }

    @RequestMapping(value = "appuser/login",method = RequestMethod.POST)
    public AppUser logInUser(@RequestBody AppUser appuser)  throws Exception{
        return appuserService.loginUser(appuser);
    }

    @RequestMapping(value = "appuser",method = RequestMethod.PUT)
    public AppUser updateAppUser(@RequestBody AppUser appuser)  throws Exception{
        return appuserService.updateAppUser(appuser);
    }

    @RequestMapping(value = "appuser/{appusercd}",method = RequestMethod.DELETE)
    public AppUser deleteAppUser(@PathVariable("appusercd")int appusercd)  throws Exception{
        return appuserService.deleteAppUser(appusercd);
    }

}