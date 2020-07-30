package com.bluebee.smartsupply.service;

import com.bluebee.smartsupply.dao.DeliveryTaskDao;
import com.bluebee.smartsupply.model.DeliveryTask;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryTaskService extends CommonService{
    Log logger= LogFactory.getLog(DeliveryTaskService.class);

    @Autowired
    DeliveryTaskDao deliverytaskDao;

    public List<DeliveryTask> getAllDeliveryTask() throws Exception{
        try {
            return  deliverytaskDao.getAllDeliveryTask();
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }

    public DeliveryTask getDeliveryTaskById(int deliverytaskid) throws Exception{
        try {
            return  deliverytaskDao.getDeliveryTaskById(deliverytaskid);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }

    public DeliveryTask addDeliveryTask(DeliveryTask deliverytask) throws Exception{
        try {
            return  deliverytaskDao.addDeliveryTask(deliverytask);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }

    public DeliveryTask updateDeliveryTask(DeliveryTask deliverytask) throws Exception{
        try {
            return  deliverytaskDao.updateDeliveryTask(deliverytask);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }

    public DeliveryTask deleteDeliveryTask(int deliverytaskid) throws Exception{
        try {
            return  deliverytaskDao.deleteDeliveryTask(deliverytaskid);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }


}