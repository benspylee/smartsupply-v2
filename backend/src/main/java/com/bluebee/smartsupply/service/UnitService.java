package com.bluebee.smartsupply.service;

import com.bluebee.smartsupply.dao.UnitDao;
import com.bluebee.smartsupply.model.Unit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitService extends CommonService{
    Log logger= LogFactory.getLog(UnitService.class);

    @Autowired
    UnitDao unitDao;

    public List<Unit> getAllUnit() throws Exception{
        try {
            return  unitDao.getAllUnit();
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }

    public Unit getUnitById(int unitcode) throws Exception{
        try {
            return  unitDao.getUnitById(unitcode);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }

    public Unit addUnit(Unit unit) throws Exception{
        try {
            return  unitDao.addUnit(unit);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }

    public Unit updateUnit(Unit unit) throws Exception{
        try {
            return  unitDao.updateUnit(unit);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }

    public Unit deleteUnit(int unitcode) throws Exception{
        try {
            return  unitDao.deleteUnit(unitcode);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }


}