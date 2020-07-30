package com.bluebee.smartsupply.dao;


import com.bluebee.smartsupply.model.OrderDelivery;
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
public class OrderDeliveryDao extends NamedParameterJdbcDaoSupport {

    static final OrderDelivery ORDERDELIVERY=new OrderDelivery();

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<OrderDelivery> getAllOrderDelivery(){
    String sql="SELECT DELIVERY_TYPE  deliverytype ,STATUS  status ,DRIVER_USER_ID  driveruserid ,ORDER_ID  orderid ,FROM_ADDR  fromaddr ,DELIVERY_ADDR  deliveryaddr ,ORDER_DELIVERY_ID  orderdeliveryid ,PICKUP_TS  pickupts ,VECHILE_CD  vechilecd  FROM VSV58378.ORDER_DELIVERY  WHERE STATUS =  1";
    return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper(OrderDelivery.class));
    }

    public OrderDelivery getOrderDeliveryById(int orderdeliveryid){
        Map<String,Object> map = new HashMap<>(1);
        map.put("orderdeliveryid",orderdeliveryid);
        String sql="SELECT DELIVERY_TYPE  deliverytype ,STATUS  status ,DRIVER_USER_ID  driveruserid ,ORDER_ID  orderid ,FROM_ADDR  fromaddr ,DELIVERY_ADDR  deliveryaddr ,ORDER_DELIVERY_ID  orderdeliveryid ,PICKUP_TS  pickupts ,VECHILE_CD  vechilecd  FROM VSV58378.ORDER_DELIVERY  WHERE ORDER_DELIVERY_ID = :orderdeliveryid ";
        return (OrderDelivery) namedParameterJdbcTemplate.queryForObject(sql,map,new BeanPropertyRowMapper(OrderDelivery.class));
    }

    public OrderDelivery getOrderDeliveryByOrderId(int orderid){
        Map<String,Object> map = new HashMap<>(1);
        map.put("orderid",orderid);
        String sql="SELECT DELIVERY_TYPE  deliverytype ,STATUS  status ,DRIVER_USER_ID  driveruserid ,ORDER_ID  orderid ,FROM_ADDR  fromaddr ,DELIVERY_ADDR  deliveryaddr ,ORDER_DELIVERY_ID  orderdeliveryid ,PICKUP_TS  pickupts ,VECHILE_CD  vechilecd  FROM VSV58378.ORDER_DELIVERY  WHERE ORDER_ID = :orderid ";
        return (OrderDelivery) namedParameterJdbcTemplate.queryForObject(sql,map,new BeanPropertyRowMapper(OrderDelivery.class));
    }

    public OrderDelivery addOrderDelivery(OrderDelivery orderdelivery){
        String sql="INSERT INTO VSV58378.ORDER_DELIVERY(DELIVERY_ADDR,DELIVERY_TYPE,DRIVER_USER_ID,FROM_ADDR,ORDER_DELIVERY_ID,ORDER_ID,PICKUP_TS,STATUS,VECHILE_CD) values(:deliveryaddr,:deliverytype,:driveruserid,:fromaddr,:orderdeliveryid,:orderid,:pickupts,:status,:vechilecd)";
        orderdelivery.setOrderdeliveryid(getSequence()+1);
        SqlParameterSource sqlpara=new BeanPropertySqlParameterSource(orderdelivery);
        namedParameterJdbcTemplate.update(sql,sqlpara);
        return getOrderDeliveryById(orderdelivery.getOrderdeliveryid());
    }


    private int getSequence(){
        String sql="SELECT max(ORDER_DELIVERY_ID) FROM VSV58378.ORDER_DELIVERY ";
        Integer seq= null;
        try {
            seq = namedParameterJdbcTemplate.queryForObject(sql,new HashMap(),Integer.class);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return seq==null?0:seq;
    }

    public OrderDelivery updateOrderDelivery(OrderDelivery orderdelivery){
        String sql="UPDATE VSV58378.ORDER_DELIVERY set DELIVERY_TYPE=:deliverytype ,STATUS=:status ,DRIVER_USER_ID=:driveruserid ,ORDER_ID=:orderid ,FROM_ADDR=:fromaddr ,DELIVERY_ADDR=:deliveryaddr ,PICKUP_TS=:pickupts ,VECHILE_CD=:vechilecd  WHERE ORDER_DELIVERY_ID = :orderdeliveryid ";
        SqlParameterSource sqlpara=new BeanPropertySqlParameterSource(orderdelivery);
        namedParameterJdbcTemplate.update(sql,sqlpara);
        return (OrderDelivery) getOrderDeliveryById(orderdelivery.getOrderdeliveryid());
    }

    public OrderDelivery deleteOrderDelivery(int orderdeliveryid){
        Map<String,Object> map = new HashMap<>(1);
        map.put("orderdeliveryid",orderdeliveryid);
        String sql="DELETE FROM VSV58378.ORDER_DELIVERY  WHERE ORDER_DELIVERY_ID = :orderdeliveryid ";
        int update = namedParameterJdbcTemplate.update(sql,map);
        return update==0?null:ORDERDELIVERY;
    }
}