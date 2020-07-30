package com.bluebee.smartsupply.service;

import com.bluebee.smartsupply.dao.OrderDeliveryDao;
import com.bluebee.smartsupply.model.OrderDelivery;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDeliveryService extends CommonService{
    Log logger= LogFactory.getLog(OrderDeliveryService.class);

    @Autowired
    OrderDeliveryDao orderdeliveryDao;

    public List<OrderDelivery> getAllOrderDelivery() throws Exception{
        try {
            return  orderdeliveryDao.getAllOrderDelivery();
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }

    public OrderDelivery getOrderDeliveryById(int orderdeliveryid) throws Exception{
        try {
            return  orderdeliveryDao.getOrderDeliveryById(orderdeliveryid);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }

    public OrderDelivery addOrderDelivery(OrderDelivery orderdelivery) throws Exception{
        try {
            return  orderdeliveryDao.addOrderDelivery(orderdelivery);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }

    public OrderDelivery updateOrderDelivery(OrderDelivery orderdelivery) throws Exception{
        try {
            return  orderdeliveryDao.updateOrderDelivery(orderdelivery);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }

    public OrderDelivery deleteOrderDelivery(int orderdeliveryid) throws Exception{
        try {
            return  orderdeliveryDao.deleteOrderDelivery(orderdeliveryid);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }


}