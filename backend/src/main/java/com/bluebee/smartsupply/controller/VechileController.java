package com.bluebee.smartsupply.controller;

import com.bluebee.smartsupply.model.Vechile;
import com.bluebee.smartsupply.model.Result;
import com.bluebee.smartsupply.service.VechileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VechileController  {

    @Autowired
    VechileService vechileService;

    @RequestMapping(value = "vechile",method = RequestMethod.GET)
    public List<Vechile> getAllVechile() throws Exception{
        return vechileService.getAllVechile();
    }

    @RequestMapping(value = "vechile/{vechilecd}",method = RequestMethod.GET)
    public Vechile getVechileById(@PathVariable("vechilecd")int vechilecd)  throws Exception{
        return vechileService.getVechileById(vechilecd);
    }

    @RequestMapping(value = "vechile",method = RequestMethod.POST)
    public Vechile addVechile(@RequestBody Vechile vechile)  throws Exception{
        return vechileService.addVechile(vechile);
    }

    @RequestMapping(value = "vechile",method = RequestMethod.PUT)
    public Vechile updateVechile(@RequestBody Vechile vechile)  throws Exception{
        return vechileService.updateVechile(vechile);
    }

    @RequestMapping(value = "vechile/{vechilecd}",method = RequestMethod.DELETE)
    public Vechile deleteVechile(@PathVariable("vechilecd")int vechilecd)  throws Exception{
        return vechileService.deleteVechile(vechilecd);
    }

    @RequestMapping(value = "vechile/template",method = RequestMethod.GET)
        public Vechile templateVechile()  throws Exception{
            return new Vechile();
        }

}