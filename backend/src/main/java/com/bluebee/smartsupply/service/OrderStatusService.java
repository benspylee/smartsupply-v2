package com.bluebee.smartsupply.service;

import com.bluebee.smartsupply.dao.OrderStatusDao;
import com.bluebee.smartsupply.model.OrderStatus;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderStatusService extends CommonService{
    Log logger= LogFactory.getLog(OrderStatusService.class);

    @Autowired
    OrderStatusDao orderstatusDao;

    public List<OrderStatus> getAllOrderStatus() throws Exception{
        try {
            return  orderstatusDao.getAllOrderStatus();
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }

    public OrderStatus getOrderStatusById(int orderstatusid) throws Exception{
        try {
            return  orderstatusDao.getOrderStatusById(orderstatusid);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }

    public OrderStatus addOrderStatus(OrderStatus orderstatus) throws Exception{
        try {
            return  orderstatusDao.addOrderStatus(orderstatus);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }

    public OrderStatus updateOrderStatus(OrderStatus orderstatus) throws Exception{
        try {
            return  orderstatusDao.updateOrderStatus(orderstatus);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }

    public OrderStatus deleteOrderStatus(int orderstatusid) throws Exception{
        try {
            return  orderstatusDao.deleteOrderStatus(orderstatusid);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }


}