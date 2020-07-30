package com.bluebee.smartsupply.service;

import com.bluebee.smartsupply.dao.OrderPaymentDao;
import com.bluebee.smartsupply.model.OrderPayment;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderPaymentService extends CommonService{
    Log logger= LogFactory.getLog(OrderPaymentService.class);

    @Autowired
    OrderPaymentDao orderpaymentDao;

    public List<OrderPayment> getAllOrderPayment() throws Exception{
        try {
            return  orderpaymentDao.getAllOrderPayment();
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }

    public OrderPayment getOrderPaymentById(int orderpaymentid) throws Exception{
        try {
            return  orderpaymentDao.getOrderPaymentById(orderpaymentid);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }

    public OrderPayment addOrderPayment(OrderPayment orderpayment) throws Exception{
        try {
            return  orderpaymentDao.addOrderPayment(orderpayment);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }

    public OrderPayment updateOrderPayment(OrderPayment orderpayment) throws Exception{
        try {
            return  orderpaymentDao.updateOrderPayment(orderpayment);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }

    public OrderPayment deleteOrderPayment(int orderpaymentid) throws Exception{
        try {
            return  orderpaymentDao.deleteOrderPayment(orderpaymentid);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }


}