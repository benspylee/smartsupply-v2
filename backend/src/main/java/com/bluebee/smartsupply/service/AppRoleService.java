package com.bluebee.smartsupply.service;

import com.bluebee.smartsupply.dao.AppRoleDao;
import com.bluebee.smartsupply.model.AppRole;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppRoleService extends CommonService{
    Log logger= LogFactory.getLog(AppRoleService.class);

    @Autowired
    AppRoleDao approleDao;

    public List<AppRole> getAllAppRole() throws Exception{
        try {
            return  approleDao.getAllAppRole();
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }

    public AppRole getAppRoleById(int approleid) throws Exception{
        try {
            return  approleDao.getAppRoleById(approleid);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }

    public AppRole addAppRole(AppRole approle) throws Exception{
        try {
            return  approleDao.addAppRole(approle);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }

    public AppRole updateAppRole(AppRole approle) throws Exception{
        try {
            return  approleDao.updateAppRole(approle);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }

    public AppRole deleteAppRole(int approleid) throws Exception{
        try {
            return  approleDao.deleteAppRole(approleid);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }


}