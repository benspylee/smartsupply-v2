package com.bluebee.smartsupply.service;

import com.bluebee.smartsupply.dao.AppUserAddressDao;
import com.bluebee.smartsupply.model.AppUserAddress;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserAddressService extends CommonService{
    Log logger= LogFactory.getLog(AppUserAddressService.class);

    @Autowired
    AppUserAddressDao appuseraddressDao;

    public List<AppUserAddress> getAllAppUserAddress() throws Exception{
        try {
            return  appuseraddressDao.getAllAppUserAddress();
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }

    public AppUserAddress getAppUserAddressById(int appuseraddressid) throws Exception{
        try {
            return  appuseraddressDao.getAppUserAddressById(appuseraddressid);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }

    public List<AppUserAddress> getAddressByUserId(int appusercode) throws Exception{
        try {
            return  appuseraddressDao.getAddressByUserId(appusercode);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }

    public AppUserAddress addAppUserAddress(AppUserAddress appuseraddress) throws Exception{
        try {
            return  appuseraddressDao.addAppUserAddress(appuseraddress);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }

    public AppUserAddress updateAppUserAddress(AppUserAddress appuseraddress) throws Exception{
        try {
            return  appuseraddressDao.updateAppUserAddress(appuseraddress);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }

    public AppUserAddress deleteAppUserAddress(int appuseraddressid) throws Exception{
        try {
            return  appuseraddressDao.deleteAppUserAddress(appuseraddressid);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }


}