package com.bluebee.smartsupply.dao;


import com.bluebee.smartsupply.model.OrderItem;
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
public class OrderItemDao extends NamedParameterJdbcDaoSupport {

    static final OrderItem ORDERITEM=new OrderItem();

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<OrderItem> getAllOrderItem(){
    String sql="SELECT STATUS  status ,ORDER_ID  orderid ,QNTY  qnty ,ITEMCD  itemcd ,UNITCODE  unitcode ,ORDER_ITEM_ID  orderitemid  FROM VSV58378.ORDER_ITEM  WHERE STATUS =  1";
    return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper(OrderItem.class));
    }

    public OrderItem getOrderItemById(int orderitemid){
        Map<String,Object> map = new HashMap<>(1);
        map.put("orderitemid",orderitemid);
        String sql="SELECT a.STATUS  status ,a.ORDER_ID  orderid ,a.QNTY  qnty ,a.ITEMCD  itemcd ,a.UNITCODE  unitcode ,a.ORDER_ITEM_ID  orderitemid ,b.ITEMNAME,b.price " +
                " FROM VSV58378.ORDER_ITEM a ,VSV58378.ITEM_INFO b where a.ITEMCD = b.ITEMCODE and a.ORDER_ITEM_ID = :orderitemid ";
        return (OrderItem) namedParameterJdbcTemplate.queryForObject(sql,map,new BeanPropertyRowMapper(OrderItem.class));
    }

    public   List<OrderItem> getOrderItemByOrderId(int orderid){
        Map<String,Object> map = new HashMap<>(1);
        map.put("orderid",orderid);
        String sql="SELECT a.STATUS  status ,a.ORDER_ID  orderid ,a.QNTY  qnty ,a.ITEMCD  itemcd ,a.UNITCODE  unitcode ,a.ORDER_ITEM_ID  orderitemid ,b.ITEMNAME,b.price " +
                "FROM VSV58378.ORDER_ITEM a ,VSV58378.ITEM_INFO b where a.ITEMCD = b.ITEMCODE and a.ORDER_ID=:orderid  ";
        return (  List<OrderItem>) namedParameterJdbcTemplate.query(sql,map,new BeanPropertyRowMapper(OrderItem.class));
    }


    public OrderItem addOrderItem(OrderItem orderitem){
        String sql="INSERT INTO VSV58378.ORDER_ITEM(ITEMCD,ORDER_ID,ORDER_ITEM_ID,QNTY,STATUS,UNITCODE) values(:itemcd,:orderid,:orderitemid,:qnty,:status,:unitcode)";
        orderitem.setOrderitemid(getSequence()+1);
        SqlParameterSource sqlpara=new BeanPropertySqlParameterSource(orderitem);
        namedParameterJdbcTemplate.update(sql,sqlpara);
        return getOrderItemById(orderitem.getOrderitemid());
    }


    private int getSequence(){
        String sql="SELECT max(ORDER_ITEM_ID) FROM VSV58378.ORDER_ITEM ";
        Integer seq= null;
        try {
            seq = namedParameterJdbcTemplate.queryForObject(sql,new HashMap(),Integer.class);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return seq==null?0:seq;
    }

    public OrderItem updateOrderItem(OrderItem orderitem){
        String sql="UPDATE VSV58378.ORDER_ITEM set STATUS=:status ,ORDER_ID=:orderid ,QNTY=:qnty ,ITEMCD=:itemcd ,UNITCODE=:unitcode  WHERE ORDER_ITEM_ID = :orderitemid ";
        SqlParameterSource sqlpara=new BeanPropertySqlParameterSource(orderitem);
        namedParameterJdbcTemplate.update(sql,sqlpara);
        return (OrderItem) getOrderItemById(orderitem.getOrderitemid());
    }

    public OrderItem deleteOrderItem(int orderitemid){
        Map<String,Object> map = new HashMap<>(1);
        map.put("orderitemid",orderitemid);
        String sql="DELETE FROM VSV58378.ORDER_ITEM  WHERE ORDER_ITEM_ID = :orderitemid ";
        int update = namedParameterJdbcTemplate.update(sql,map);
        return update==0?null:ORDERITEM;
    }
}