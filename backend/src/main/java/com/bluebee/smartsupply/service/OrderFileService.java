package com.bluebee.smartsupply.service;

import com.bluebee.smartsupply.dao.OrderFileDao;
import com.bluebee.smartsupply.model.OrderFile;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderFileService extends CommonService{
    Log logger= LogFactory.getLog(OrderFileService.class);

    @Autowired
    OrderFileDao orderfileDao;

    public List<OrderFile> getAllOrderFile() throws Exception{
        try {
            return  orderfileDao.getAllOrderFile();
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }

    public OrderFile getOrderFileById(int orderfileid) throws Exception{
        try {
            return  orderfileDao.getOrderFileById(orderfileid);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }

    public OrderFile addOrderFile(OrderFile orderfile) throws Exception{
        try {
            return  orderfileDao.addOrderFile(orderfile);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }

    public OrderFile updateOrderFile(OrderFile orderfile) throws Exception{
        try {
            return  orderfileDao.updateOrderFile(orderfile);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }

    public OrderFile deleteOrderFile(int orderfileid) throws Exception{
        try {
            return  orderfileDao.deleteOrderFile(orderfileid);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }


}