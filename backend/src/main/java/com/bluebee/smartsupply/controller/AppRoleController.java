package com.bluebee.smartsupply.controller;

import com.bluebee.smartsupply.model.AppRole;
import com.bluebee.smartsupply.model.Result;
import com.bluebee.smartsupply.service.AppRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AppRoleController  {

    @Autowired
    AppRoleService approleService;

    @RequestMapping(value = "approle",method = RequestMethod.GET)
    public List<AppRole> getAllAppRole() throws Exception{
        return approleService.getAllAppRole();
    }

    @RequestMapping(value = "approle/{approlecd}",method = RequestMethod.GET)
    public AppRole getAppRoleById(@PathVariable("approlecd")int approlecd)  throws Exception{
        return approleService.getAppRoleById(approlecd);
    }

    @RequestMapping(value = "approle",method = RequestMethod.POST)
    public AppRole addAppRole(@RequestBody AppRole approle)  throws Exception{
        return approleService.addAppRole(approle);
    }

    @RequestMapping(value = "approle",method = RequestMethod.PUT)
    public AppRole updateAppRole(@RequestBody AppRole approle)  throws Exception{
        return approleService.updateAppRole(approle);
    }

    @RequestMapping(value = "approle/{approlecd}",method = RequestMethod.DELETE)
    public AppRole deleteAppRole(@PathVariable("approlecd")int approlecd)  throws Exception{
        return approleService.deleteAppRole(approlecd);
    }

}