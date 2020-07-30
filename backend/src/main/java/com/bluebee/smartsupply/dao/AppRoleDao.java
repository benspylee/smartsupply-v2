package com.bluebee.smartsupply.dao;


import com.bluebee.smartsupply.model.AppRole;
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
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class AppRoleDao extends NamedParameterJdbcDaoSupport {

    static final AppRole APPROLE=new AppRole();

    @Autowired
    private DataSource dataSource;


    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<AppRole> getAllAppRole(){
    String sql="SELECT STATUS ,APP_ROLE_NAME APPROLENAME ,APP_ROLE_CD APPROLECD  FROM VSV58378.APP_ROLE  WHERE STATUS =  1";
    return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper(AppRole.class));
    }

    public AppRole getAppRoleById(int approlecd){
        Map<String,Object> map = new HashMap<>(1);
        map.put("approlecd",approlecd);
        String sql="SELECT STATUS ,APP_ROLE_NAME APPROLENAME ,APP_ROLE_CD APPROLECD  FROM VSV58378.APP_ROLE  WHERE APP_ROLE_CD = :approlecd ";
        return (AppRole) namedParameterJdbcTemplate.queryForObject(sql,map,new BeanPropertyRowMapper(AppRole.class));
    }

    public AppRole addAppRole(AppRole approle){
        String sql="INSERT INTO VSV58378.APP_ROLE(APP_ROLE_CD,APP_ROLE_NAME,STATUS) values(:approlecd,:approlename,:status)";
        approle.setApprolecd(getSequence()+1);
        SqlParameterSource sqlpara=new BeanPropertySqlParameterSource(approle);
        namedParameterJdbcTemplate.update(sql,sqlpara);
        return getAppRoleById(approle.getApprolecd());
    }


    private int getSequence(){
        String sql="SELECT max(APP_ROLE_CD) FROM VSV58378.APP_ROLE ";
        Integer seq= null;
        try {
            seq = namedParameterJdbcTemplate.queryForObject(sql,new HashMap(),Integer.class);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return seq==null?0:seq;
    }

    public AppRole updateAppRole(AppRole approle){
        String sql="UPDATE VSV58378.APP_ROLE set STATUS=:status ,APP_ROLE_NAME=:approlename  WHERE APP_ROLE_CD = :approlecd ";
        SqlParameterSource sqlpara=new BeanPropertySqlParameterSource(approle);
        namedParameterJdbcTemplate.update(sql,sqlpara);
        return (AppRole) getAppRoleById(approle.getApprolecd());
    }

    public AppRole deleteAppRole(int approlecd){
        Map<String,Object> map = new HashMap<>(1);
        map.put("approlecd",approlecd);
        String sql="DELETE FROM VSV58378.APP_ROLE  WHERE APP_ROLE_CD = :approlecd ";
        int update = namedParameterJdbcTemplate.update(sql,map);
        return update==0?null:APPROLE;
    }
}