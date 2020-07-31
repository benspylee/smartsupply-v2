package com.bluebee.smartsupply.dao;


import com.bluebee.smartsupply.model.OrderFile;
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
public class OrderFileDao extends NamedParameterJdbcDaoSupport {

    static final OrderFile ORDERFILE=new OrderFile();

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<OrderFile> getAllOrderFile(){
    String sql="SELECT INFECT_REPORT  infectreport ,STATUS  status ,ORDER_ID  orderid ,ORDER_FILE_ID  orderfileid ,INVOICE_FILE  invoicefile  FROM VSV58378.ORDER_FILE  WHERE STATUS =  1";
    return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper(OrderFile.class));
    }

    public OrderFile getOrderFileById(int orderfileid){
        Map<String,Object> map = new HashMap<>(1);
        map.put("orderfileid",orderfileid);
        String sql="SELECT INFECT_REPORT  infectreport ,STATUS  status ,ORDER_ID  orderid ,ORDER_FILE_ID  orderfileid ,INVOICE_FILE  invoicefile  FROM VSV58378.ORDER_FILE  WHERE ORDER_FILE_ID = :orderfileid ";
        return (OrderFile) namedParameterJdbcTemplate.queryForObject(sql,map,new BeanPropertyRowMapper(OrderFile.class));
    }
    public OrderFile getOrderFileByOrderId(int orderid){
        Map<String,Object> map = new HashMap<>(1);
        map.put("orderid",orderid);
        String sql="SELECT INFECT_REPORT  infectreport ,STATUS  status ,ORDER_ID  orderid ,ORDER_FILE_ID  orderfileid ,INVOICE_FILE  invoicefile  FROM VSV58378.ORDER_FILE  WHERE ORDER_ID = :orderid ";
        try {
            return (OrderFile) namedParameterJdbcTemplate.queryForObject(sql,map,new BeanPropertyRowMapper(OrderFile.class));
        } catch (DataAccessException e) {
            return null;
        }
    }

    public OrderFile addOrderFile(OrderFile orderfile){
        String sql="INSERT INTO VSV58378.ORDER_FILE(INFECT_REPORT,INVOICE_FILE,ORDER_FILE_ID,ORDER_ID,STATUS) values(:infectreport,:invoicefile,:orderfileid,:orderid,:status)";
        orderfile.setOrderfileid(getSequence()+1);
        SqlParameterSource sqlpara=new BeanPropertySqlParameterSource(orderfile);
        namedParameterJdbcTemplate.update(sql,sqlpara);
        return getOrderFileById(orderfile.getOrderfileid());
    }


    private int getSequence(){
        String sql="SELECT max(ORDER_FILE_ID) FROM VSV58378.ORDER_FILE ";
        Integer seq= null;
        try {
            seq = namedParameterJdbcTemplate.queryForObject(sql,new HashMap(),Integer.class);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return seq==null?0:seq;
    }

    public OrderFile updateOrderFile(OrderFile orderfile){
        String sql="UPDATE VSV58378.ORDER_FILE set INFECT_REPORT=:infectreport ,STATUS=:status ,ORDER_ID=:orderid ,INVOICE_FILE=:invoicefile  WHERE ORDER_FILE_ID = :orderfileid ";
        SqlParameterSource sqlpara=new BeanPropertySqlParameterSource(orderfile);
        namedParameterJdbcTemplate.update(sql,sqlpara);
        return (OrderFile) getOrderFileById(orderfile.getOrderfileid());
    }

    public OrderFile deleteOrderFile(int orderfileid){
        Map<String,Object> map = new HashMap<>(1);
        map.put("orderfileid",orderfileid);
        String sql="DELETE FROM VSV58378.ORDER_FILE  WHERE ORDER_FILE_ID = :orderfileid ";
        int update = namedParameterJdbcTemplate.update(sql,map);
        return update==0?null:ORDERFILE;
    }
}