package com.bluebee.smartsupply.service;

import com.bluebee.smartsupply.dao.VechileDao;
import com.bluebee.smartsupply.model.Vechile;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VechileService extends CommonService{
    Log logger= LogFactory.getLog(VechileService.class);

    @Autowired
    VechileDao vechileDao;

    public List<Vechile> getAllVechile() throws Exception{
        try {
            return  vechileDao.getAllVechile();
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }

    public Vechile getVechileById(int vechilecd) throws Exception{
        try {
            return  vechileDao.getVechileById(vechilecd);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }

    public List<Vechile> getVechileByUserId(int appusercd) throws Exception{
        try {
            return  vechileDao.getVechileByUserId(appusercd);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }

    public Vechile addVechile(Vechile vechile) throws Exception{
        try {
            return  vechileDao.addVechile(vechile);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }



    public Vechile updateVechile(Vechile vechile) throws Exception{
        try {
            return  vechileDao.updateVechile(vechile);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }

    public Vechile deleteVechile(int vechilecd) throws Exception{
        try {
            return  vechileDao.deleteVechile(vechilecd);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }


}