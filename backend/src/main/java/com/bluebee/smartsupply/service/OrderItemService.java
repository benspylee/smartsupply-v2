package com.bluebee.smartsupply.service;

import com.bluebee.smartsupply.dao.OrderItemDao;
import com.bluebee.smartsupply.model.OrderItem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService extends CommonService{
    Log logger= LogFactory.getLog(OrderItemService.class);

    @Autowired
    OrderItemDao orderitemDao;

    public List<OrderItem> getAllOrderItem() throws Exception{
        try {
            return  orderitemDao.getAllOrderItem();
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }

    public OrderItem getOrderItemById(int orderitemid) throws Exception{
        try {
            return  orderitemDao.getOrderItemById(orderitemid);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }

    public   List<OrderItem> getOrderItemByOrderId(int orderid) throws Exception{
        try {
            return  orderitemDao.getOrderItemByOrderId(orderid);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }
    public OrderItem addOrderItem(OrderItem orderitem) throws Exception{
        try {
            return  orderitemDao.addOrderItem(orderitem);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }

    public OrderItem updateOrderItem(OrderItem orderitem) throws Exception{
        try {
            return  orderitemDao.updateOrderItem(orderitem);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }

    public OrderItem deleteOrderItem(int orderitemid) throws Exception{
        try {
            return  orderitemDao.deleteOrderItem(orderitemid);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }


}