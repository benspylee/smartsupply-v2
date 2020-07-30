package com.bluebee.smartsupply.service;

import com.bluebee.smartsupply.dao.AppUserDao;
import com.bluebee.smartsupply.model.AppUser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserService extends CommonService{
    Log logger= LogFactory.getLog(AppUserService.class);

    @Autowired
    AppUserDao appuserDao;

    public List<AppUser> getAllAppUser() throws Exception{
        try {
            return  appuserDao.getAllAppUser();
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }

    public AppUser getAppUserById(int appuserid) throws Exception{
        try {
            return  appuserDao.getAppUserById(appuserid);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }

    public AppUser addAppUser(AppUser appuser) throws Exception{
        try {
            return  appuserDao.addAppUser(appuser);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }

    public AppUser loginUser(AppUser appuser) throws Exception{
        try {
            return  appuserDao.loginUser(appuser);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }


    public AppUser updateAppUser(AppUser appuser) throws Exception{
        try {
            return  appuserDao.updateAppUser(appuser);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }

    public AppUser deleteAppUser(int appuserid) throws Exception{
        try {
            return  appuserDao.deleteAppUser(appuserid);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }


}