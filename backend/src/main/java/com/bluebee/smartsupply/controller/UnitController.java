package com.bluebee.smartsupply.controller;

import com.bluebee.smartsupply.model.Unit;
import com.bluebee.smartsupply.model.Result;
import com.bluebee.smartsupply.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UnitController  {

    @Autowired
    UnitService unitService;

    @RequestMapping(value = "unit",method = RequestMethod.GET)
    public List<Unit> getAllUnit() throws Exception{
        return unitService.getAllUnit();
    }

    @RequestMapping(value = "unit/{unitcode}",method = RequestMethod.GET)
    public Unit getUnitById(@PathVariable("unitcode")int unitcode)  throws Exception{
        return unitService.getUnitById(unitcode);
    }

    @RequestMapping(value = "unit",method = RequestMethod.POST)
    public Unit addUnit(@RequestBody Unit unit)  throws Exception{
        return unitService.addUnit(unit);
    }

    @RequestMapping(value = "unit",method = RequestMethod.PUT)
    public Unit updateUnit(@RequestBody Unit unit)  throws Exception{
        return unitService.updateUnit(unit);
    }

    @RequestMapping(value = "unit/{unitcode}",method = RequestMethod.DELETE)
    public Unit deleteUnit(@PathVariable("unitcode")int unitcode)  throws Exception{
        return unitService.deleteUnit(unitcode);
    }

    @RequestMapping(value = "unit/template",method = RequestMethod.GET)
        public Unit templateUnit()  throws Exception{
            return new Unit();
        }

}