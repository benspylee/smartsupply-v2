package com.bluebee.smartsupply.dao;


import com.bluebee.smartsupply.model.OrderStatus;
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
public class OrderStatusDao extends NamedParameterJdbcDaoSupport {

    static final OrderStatus ORDERSTATUS=new OrderStatus();

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<OrderStatus> getAllOrderStatus(){
    String sql="SELECT ORDER_STATUS_ID  orderstatusid ,UPDATE_UID  updateuid ,ORDER_ID  orderid ,ORDER_STATUS_CD  orderstatuscd ,ORDER_TS  orderts  FROM VSV58378.ORDER_STATUS  ";
    return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper(OrderStatus.class));
    }

    public OrderStatus getOrderStatusById(int orderstatusid){
        Map<String,Object> map = new HashMap<>(1);
        map.put("orderstatusid",orderstatusid);
        String sql="SELECT ORDER_STATUS_ID  orderstatusid ,UPDATE_UID  updateuid ,ORDER_ID  orderid ,ORDER_STATUS_CD  orderstatuscd ,ORDER_TS  orderts  FROM VSV58378.ORDER_STATUS  WHERE ORDER_STATUS_ID = :orderstatusid ";
        return (OrderStatus) namedParameterJdbcTemplate.queryForObject(sql,map,new BeanPropertyRowMapper(OrderStatus.class));
    }

    public OrderStatus addOrderStatus(OrderStatus orderstatus){
        String sql="INSERT INTO VSV58378.ORDER_STATUS(ORDER_ID,ORDER_STATUS_CD,ORDER_STATUS_ID,ORDER_TS,UPDATE_UID) values(:orderid,:orderstatuscd,:orderstatusid,:orderts,:updateuid)";
        orderstatus.setOrderstatusid(getSequence()+1);
        SqlParameterSource sqlpara=new BeanPropertySqlParameterSource(orderstatus);
        namedParameterJdbcTemplate.update(sql,sqlpara);
        return getOrderStatusById(orderstatus.getOrderstatusid());
    }


    private int getSequence(){
        String sql="SELECT max(ORDER_STATUS_ID) FROM VSV58378.ORDER_STATUS ";
        Integer seq= null;
        try {
            seq = namedParameterJdbcTemplate.queryForObject(sql,new HashMap(),Integer.class);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return seq==null?0:seq;
    }

    public OrderStatus updateOrderStatus(OrderStatus orderstatus){
        String sql="UPDATE VSV58378.ORDER_STATUS set UPDATE_UID  =:updateuid ,ORDER_ID=:orderid ,ORDER_STATUS_CD=:orderstatuscd ,ORDER_TS=:orderts  WHERE ORDER_STATUS_ID = :orderstatusid ";
        SqlParameterSource sqlpara=new BeanPropertySqlParameterSource(orderstatus);
        namedParameterJdbcTemplate.update(sql,sqlpara);
        return (OrderStatus) getOrderStatusById(orderstatus.getOrderstatuscd());
    }

    public OrderStatus deleteOrderStatus(int orderstatusid){
        Map<String,Object> map = new HashMap<>(1);
        map.put("orderstatusid",orderstatusid);
        String sql="DELETE FROM VSV58378.ORDER_STATUS  WHERE ORDER_STATUS_ID = :orderstatusid ";
        int update = namedParameterJdbcTemplate.update(sql,map);
        return update==0?null:ORDERSTATUS;
    }
}