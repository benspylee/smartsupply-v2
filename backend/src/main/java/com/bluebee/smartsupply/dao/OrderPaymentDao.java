package com.bluebee.smartsupply.dao;


import com.bluebee.smartsupply.model.OrderPayment;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderPaymentDao extends NamedParameterJdbcDaoSupport {

    static final OrderPayment ORDERPAYMENT=new OrderPayment();

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<OrderPayment> getAllOrderPayment(){
    String sql="SELECT STATUS  status ,ORDER_ID  orderid ,TOTALAMT  totalamt ,PAYMODE  paymode ,ORDER_PAYMENT_ID  orderpaymentid ,ISPAID  ispaid  FROM VSV58378.ORDER_PAYMENT  WHERE STATUS =  1";
    return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper(OrderPayment.class));
    }

    public OrderPayment getOrderPaymentById(int orderpaymentid){
        Map<String,Object> map = new HashMap<>(1);
        map.put("orderpaymentid",orderpaymentid);
        String sql="SELECT STATUS  status ,ORDER_ID  orderid ,TOTALAMT  totalamt ,PAYMODE  paymode ,ORDER_PAYMENT_ID  orderpaymentid ,ISPAID  ispaid  FROM VSV58378.ORDER_PAYMENT  WHERE ORDER_PAYMENT_ID = :orderpaymentid ";
        return (OrderPayment) namedParameterJdbcTemplate.queryForObject(sql,map,new BeanPropertyRowMapper(OrderPayment.class));
    }

    public OrderPayment addOrderPayment(OrderPayment orderpayment){
        String sql="INSERT INTO VSV58378.ORDER_PAYMENT(ISPAID,ORDER_ID,ORDER_PAYMENT_ID,PAYMODE,STATUS,TOTALAMT) values(:ispaid,:orderid,:orderpaymentid,:paymode,:status,:totalamt)";
        orderpayment.setOrderpaymentid(getSequence()+1);
        SqlParameterSource sqlpara=new BeanPropertySqlParameterSource(orderpayment);
        namedParameterJdbcTemplate.update(sql,sqlpara);
        return getOrderPaymentById(orderpayment.getOrderpaymentid());
    }


    private int getSequence(){
        String sql="SELECT max(ORDER_PAYMENT_ID) FROM VSV58378.ORDER_PAYMENT ";
        Integer seq= null;
        try {
            seq = namedParameterJdbcTemplate.queryForObject(sql,new HashMap(),Integer.class);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return seq==null?0:seq;
    }

    public OrderPayment updateOrderPayment(OrderPayment orderpayment){
        String sql="UPDATE VSV58378.ORDER_PAYMENT set STATUS=:status ,ORDER_ID=:orderid ,TOTALAMT=:totalamt ,PAYMODE=:paymode ,ISPAID=:ispaid  WHERE ORDER_PAYMENT_ID = :orderpaymentid ";
        SqlParameterSource sqlpara=new BeanPropertySqlParameterSource(orderpayment);
        namedParameterJdbcTemplate.update(sql,sqlpara);
        return (OrderPayment) getOrderPaymentById(orderpayment.getOrderpaymentid());
    }

    public OrderPayment deleteOrderPayment(int orderpaymentid){
        Map<String,Object> map = new HashMap<>(1);
        map.put("orderpaymentid",orderpaymentid);
        String sql="DELETE FROM VSV58378.ORDER_PAYMENT  WHERE ORDER_PAYMENT_ID = :orderpaymentid ";
        int update = namedParameterJdbcTemplate.update(sql,map);
        return update==0?null:ORDERPAYMENT;
    }
}