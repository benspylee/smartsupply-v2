package com.bluebee.smartsupply.dao;


import com.bluebee.smartsupply.model.DeliveryTask;
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
public class DeliveryTaskDao extends NamedParameterJdbcDaoSupport {

    static final DeliveryTask DELIVERYTASK=new DeliveryTask();

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<DeliveryTask> getAllDeliveryTask(){
    String sql="SELECT STATUS  status ,APPUSERCD  appusercd ,ORDER_ID  orderid ,ACCEPT_STAT_CD  acceptstatcd ,OPEN_TASK  opentask ,DELIVERY_TASK_ID  deliverytaskid  FROM VSV58378.DELIVERY_TASKS  WHERE STATUS =  1";
    return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper(DeliveryTask.class));
    }

    public DeliveryTask getDeliveryTaskById(int deliverytaskid){
        Map<String,Object> map = new HashMap<>(1);
        map.put("deliverytaskid",deliverytaskid);
        String sql="SELECT STATUS  status ,APPUSERCD  appusercd ,ORDER_ID  orderid ,ACCEPT_STAT_CD  acceptstatcd ,OPEN_TASK  opentask ,DELIVERY_TASK_ID  deliverytaskid  FROM VSV58378.DELIVERY_TASKS  WHERE DELIVERY_TASK_ID = :deliverytaskid ";
        return (DeliveryTask) namedParameterJdbcTemplate.queryForObject(sql,map,new BeanPropertyRowMapper(DeliveryTask.class));
    }

    public DeliveryTask getDeliveryTaskByOrderId(int orderid){
        Map<String,Object> map = new HashMap<>(1);
        map.put("orderid",orderid);
        String sql="SELECT STATUS  status ,APPUSERCD  appusercd ,ORDER_ID  orderid ,ACCEPT_STAT_CD  acceptstatcd ,OPEN_TASK  opentask ,DELIVERY_TASK_ID  deliverytaskid  FROM VSV58378.DELIVERY_TASKS  WHERE ORDER_ID = :orderid ";
        return (DeliveryTask) namedParameterJdbcTemplate.queryForObject(sql,map,new BeanPropertyRowMapper(DeliveryTask.class));
    }

    public DeliveryTask addDeliveryTask(DeliveryTask deliverytask){
        String sql="INSERT INTO VSV58378.DELIVERY_TASKS(ACCEPT_STAT_CD,APPUSERCD,DELIVERY_TASK_ID,OPEN_TASK,ORDER_ID,STATUS) values(:acceptstatcd,:appusercd,:deliverytaskid,:opentask,:orderid,:status)";
        deliverytask.setDeliverytaskid(getSequence()+1);
        SqlParameterSource sqlpara=new BeanPropertySqlParameterSource(deliverytask);
        namedParameterJdbcTemplate.update(sql,sqlpara);
        return getDeliveryTaskById(deliverytask.getDeliverytaskid());
    }


    private int getSequence(){
        String sql="SELECT max(DELIVERY_TASK_ID) FROM VSV58378.DELIVERY_TASKS ";
        Integer seq= null;
        try {
            seq = namedParameterJdbcTemplate.queryForObject(sql,new HashMap(),Integer.class);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return seq==null?0:seq;
    }

    public DeliveryTask updateDeliveryTask(DeliveryTask deliverytask){
        String sql="UPDATE VSV58378.DELIVERY_TASKS set STATUS=:status ,APPUSERCD=:appusercd ,ORDER_ID=:orderid ,ACCEPT_STAT_CD=:acceptstatcd ,OPEN_TASK=:opentask  WHERE DELIVERY_TASK_ID = :deliverytaskid ";
        SqlParameterSource sqlpara=new BeanPropertySqlParameterSource(deliverytask);
        namedParameterJdbcTemplate.update(sql,sqlpara);
        return (DeliveryTask) getDeliveryTaskById(deliverytask.getDeliverytaskid());
    }

    public DeliveryTask deleteDeliveryTask(int deliverytaskid){
        Map<String,Object> map = new HashMap<>(1);
        map.put("deliverytaskid",deliverytaskid);
        String sql="DELETE FROM VSV58378.DELIVERY_TASKS  WHERE DELIVERY_TASK_ID = :deliverytaskid ";
        int update = namedParameterJdbcTemplate.update(sql,map);
        return update==0?null:DELIVERYTASK;
    }
}