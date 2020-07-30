package com.bluebee.smartsupply.dao;


import com.bluebee.smartsupply.model.Vechile;
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
public class VechileDao extends NamedParameterJdbcDaoSupport {

    static final Vechile VECHILE=new Vechile();

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Vechile> getAllVechile(){
    String sql="SELECT ADDRESS_3  address3 ,STATUS  status ,OWNER_NAME  ownername ,ADDESS_1  addess1 ,VECHILE_REGNO  vechileregno ,ZIPCODE  zipcode ,APP_USER_CD  appusercd ,VECHILE_CD  vechilecd ,MOBILENO  mobileno ,ADDRESS_2  address2  FROM VSV58378.VECHILE  WHERE STATUS =  1";
    return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper(Vechile.class));
    }

    public Vechile getVechileById(int vechilecd){
        Map<String,Object> map = new HashMap<>(1);
        map.put("vechilecd",vechilecd);
        String sql="SELECT ADDRESS_3  address3 ,STATUS  status ,OWNER_NAME  ownername ,ADDESS_1  addess1 ,VECHILE_REGNO  vechileregno ,ZIPCODE  zipcode ,APP_USER_CD  appusercd ,VECHILE_CD  vechilecd ,MOBILENO  mobileno ,ADDRESS_2  address2  FROM VSV58378.VECHILE  WHERE VECHILE_CD = :vechilecd ";
        return (Vechile) namedParameterJdbcTemplate.queryForObject(sql,map,new BeanPropertyRowMapper(Vechile.class));
    }

    public List<Vechile> getVechileByUserId(int appusercd) throws Exception{
        Map<String,Object> map = new HashMap<>(1);
        map.put("appusercd",appusercd);
        String sql="SELECT ADDRESS_3  address3 ,STATUS  status ,OWNER_NAME  ownername ,ADDESS_1  addess1 ,VECHILE_REGNO  vechileregno ,ZIPCODE  zipcode ,APP_USER_CD  appusercd ,VECHILE_CD  vechilecd ,MOBILENO  mobileno ,ADDRESS_2  address2  FROM VSV58378.VECHILE  WHERE APP_USER_CD = :appusercd ";
        return (List<Vechile>) namedParameterJdbcTemplate.query(sql,map,new BeanPropertyRowMapper(Vechile.class));
    }

    public Vechile addVechile(Vechile vechile){
        String sql="INSERT INTO VSV58378.VECHILE(ADDESS_1,ADDRESS_2,ADDRESS_3,APP_USER_CD,MOBILENO,OWNER_NAME,STATUS,VECHILE_CD,VECHILE_REGNO,ZIPCODE) values(:addess1,:address2,:address3,:appusercd,:mobileno,:ownername,:status,:vechilecd,:vechileregno,:zipcode)";
        vechile.setVechilecd(getSequence()+1);
        SqlParameterSource sqlpara=new BeanPropertySqlParameterSource(vechile);
        namedParameterJdbcTemplate.update(sql,sqlpara);
        return getVechileById(vechile.getVechilecd());
    }


    private int getSequence(){
        String sql="SELECT max(VECHILE_CD) FROM VSV58378.VECHILE ";
        Integer seq= null;
        try {
            seq = namedParameterJdbcTemplate.queryForObject(sql,new HashMap(),Integer.class);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return seq==null?0:seq;
    }

    public Vechile updateVechile(Vechile vechile){
        String sql="UPDATE VSV58378.VECHILE set ADDRESS_3=:address3 ,STATUS=:status ,OWNER_NAME=:ownername ,ADDESS_1=:addess1 ,VECHILE_REGNO=:vechileregno ,ZIPCODE=:zipcode ,APP_USER_CD=:appusercd ,MOBILENO=:mobileno ,ADDRESS_2=:address2  WHERE VECHILE_CD = :vechilecd ";
        SqlParameterSource sqlpara=new BeanPropertySqlParameterSource(vechile);
        namedParameterJdbcTemplate.update(sql,sqlpara);
        return (Vechile) getVechileById(vechile.getVechilecd());
    }

    public Vechile deleteVechile(int vechilecd){
        Map<String,Object> map = new HashMap<>(1);
        map.put("vechilecd",vechilecd);
        String sql="DELETE FROM VSV58378.VECHILE  WHERE VECHILE_CD = :vechilecd ";
        int update = namedParameterJdbcTemplate.update(sql,map);
        return update==0?null:VECHILE;
    }
}