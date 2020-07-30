package com.bluebee.smartsupply.dao;


import com.bluebee.smartsupply.model.AppUserAddress;
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
public class AppUserAddressDao extends NamedParameterJdbcDaoSupport {

    static final AppUserAddress APPUSERADDRESS=new AppUserAddress();

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<AppUserAddress> getAllAppUserAddress(){
    String sql="SELECT ADDRESS_3  address3 ,STATUS  status ,IS_PRIMARY  isprimary ,ADDESS_1  addess1 ,ZIPCODE  zipcode ,APP_USER_CD  appusercd ,APP_USER_ADDR_CD  appuseraddrcd ,ADDRESS_2  address2  FROM VSV58378.APP_USER_ADDRESS  WHERE STATUS =  1";
    return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper(AppUserAddress.class));
    }

    public AppUserAddress getAppUserAddressById(int appuseraddrcd){
        Map<String,Object> map = new HashMap<>(1);
        map.put("appuseraddrcd",appuseraddrcd);
        String sql="SELECT ADDRESS_3  address3 ,STATUS  status ,IS_PRIMARY  isprimary ,ADDESS_1  addess1 ,ZIPCODE  zipcode ,APP_USER_CD  appusercd ,APP_USER_ADDR_CD  appuseraddrcd ,ADDRESS_2  address2  FROM VSV58378.APP_USER_ADDRESS  WHERE APP_USER_ADDR_CD = :appuseraddrcd ";
        return (AppUserAddress) namedParameterJdbcTemplate.queryForObject(sql,map,new BeanPropertyRowMapper(AppUserAddress.class));
    }

    public List<AppUserAddress> getAddressByUserId(int appusercode) throws Exception{
        Map<String,Object> map = new HashMap<>(1);
        map.put("appusercd",appusercode);
        String sql="SELECT ADDRESS_3  address3 ,STATUS  status ,IS_PRIMARY  isprimary ,ADDESS_1  addess1 ,ZIPCODE  zipcode ,APP_USER_CD  appusercd ,APP_USER_ADDR_CD  appuseraddrcd ,ADDRESS_2  address2  FROM VSV58378.APP_USER_ADDRESS  WHERE STATUS =  1 and APP_USER_CD = :appusercd";
        return namedParameterJdbcTemplate.query(sql, map,new BeanPropertyRowMapper(AppUserAddress.class));

    }

    public AppUserAddress addAppUserAddress(AppUserAddress appuseraddress){
        String sql="INSERT INTO VSV58378.APP_USER_ADDRESS(ADDESS_1,ADDRESS_2,ADDRESS_3,APP_USER_ADDR_CD,APP_USER_CD,IS_PRIMARY,STATUS,ZIPCODE) values(:addess1,:address2,:address3,:appuseraddrcd,:appusercd,:isprimary,:status,:zipcode)";
        appuseraddress.setAppuseraddrcd(getSequence()+1);
        SqlParameterSource sqlpara=new BeanPropertySqlParameterSource(appuseraddress);
        namedParameterJdbcTemplate.update(sql,sqlpara);
        return getAppUserAddressById(appuseraddress.getAppuseraddrcd());
    }


    private int getSequence(){
        String sql="SELECT max(APP_USER_ADDR_CD) FROM VSV58378.APP_USER_ADDRESS ";
        Integer seq= null;
        try {
            seq = namedParameterJdbcTemplate.queryForObject(sql,new HashMap(),Integer.class);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return seq==null?0:seq;
    }

    public AppUserAddress updateAppUserAddress(AppUserAddress appuseraddress){
        String sql="UPDATE VSV58378.APP_USER_ADDRESS set ADDRESS_3=:address3 ,STATUS=:status ,IS_PRIMARY=:isprimary ,ADDESS_1=:addess1 ,ZIPCODE=:zipcode ,APP_USER_CD=:appusercd ,ADDRESS_2=:address2  WHERE APP_USER_ADDR_CD = :appuseraddrcd ";
        SqlParameterSource sqlpara=new BeanPropertySqlParameterSource(appuseraddress);
        namedParameterJdbcTemplate.update(sql,sqlpara);
        return (AppUserAddress) getAppUserAddressById(appuseraddress.getAppuseraddrcd());
    }

    public AppUserAddress deleteAppUserAddress(int appuseraddrcd){
        Map<String,Object> map = new HashMap<>(1);
        map.put("appuseraddrcd",appuseraddrcd);
        String sql="DELETE FROM VSV58378.APP_USER_ADDRESS  WHERE APP_USER_ADDR_CD = :appuseraddrcd ";
        int update = namedParameterJdbcTemplate.update(sql,map);
        return update==0?null:APPUSERADDRESS;
    }
}