package com.bluebee.smartsupply.dao;


import com.bluebee.smartsupply.handler.DuplicateUserException;
import com.bluebee.smartsupply.model.AppUser;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.util.DigestUtils;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AppUserDao extends NamedParameterJdbcDaoSupport {

    static final AppUser APPUSER=new AppUser();

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<AppUser> getAllAppUser(){
    String sql="SELECT STATUS  status  ,LASTNAME  lastname ,APP_USER_CD  appusercd ,FIRSTNAME  firstname ,APP_USER_ROLE_CD  appuserrolecd ,MOBILENO  mobileno ,EMAILID  emailid  FROM VSV58378.APP_USER  WHERE STATUS =  1";
    return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper(AppUser.class));
    }

    public AppUser getAppUserById(int appusercd){
        Map<String,Object> map = new HashMap<>(1);
        map.put("appusercd",appusercd);
        String sql="SELECT STATUS  status  ,LASTNAME  lastname ,APP_USER_CD  appusercd ,FIRSTNAME  firstname ,APP_USER_ROLE_CD  appuserrolecd ,MOBILENO  mobileno ,EMAILID  emailid  FROM VSV58378.APP_USER  WHERE APP_USER_CD = :appusercd ";
        return (AppUser) namedParameterJdbcTemplate.queryForObject(sql,map,new BeanPropertyRowMapper(AppUser.class));
    }

    public AppUser addAppUser(AppUser appuser) throws Exception{
       if(duplicatUserCheck(appuser)){
           throw new DuplicateUserException("User Already found..!");
       }
        appuser.setPassword(encodePass(appuser.getPassword()));
        String sql="INSERT INTO VSV58378.APP_USER(APP_USER_CD,APP_USER_ROLE_CD,EMAILID,FIRSTNAME,LASTNAME,MOBILENO,PASSWORD,STATUS) values(:appusercd,:appuserrolecd,:emailid,:firstname,:lastname,:mobileno,:password,:status)";
        appuser.setAppusercd(getSequence()+1);
        SqlParameterSource sqlpara=new BeanPropertySqlParameterSource(appuser);
        namedParameterJdbcTemplate.update(sql,sqlpara);
        return getAppUserById(appuser.getAppusercd());
    }

    private String encodePass(String pass) {
        String passmd5Hex = DigestUtils
                .md5DigestAsHex(pass.getBytes());
       return passmd5Hex;
    }

    public AppUser loginUser(AppUser appuser) throws Exception{
        appuser.setPassword(encodePass(appuser.getPassword()));
        String sql="SELECT STATUS  status  ,LASTNAME  lastname ,APP_USER_CD  appusercd ,FIRSTNAME  firstname ,APP_USER_ROLE_CD  appuserrolecd ,MOBILENO  mobileno ,EMAILID  emailid  FROM VSV58378.APP_USER  WHERE STATUS =  1 and PASSWORD=:password and  ( EMAILID=:emailid or MOBILENO=:mobileno)";
        SqlParameterSource sqlpara=new BeanPropertySqlParameterSource(appuser);
        try {
            return namedParameterJdbcTemplate.queryForObject(sql,sqlpara,new BeanPropertyRowMapper<>(AppUser.class));
        } catch (EmptyResultDataAccessException e) {
             throw new Exception("User validation failed");
        }
    }


    private boolean duplicatUserCheck(AppUser appUser){
        String sql="SELECT 1 from VSV58378.APP_USER where EMAILID=:emailid or MOBILENO=:mobileno";
        SqlParameterSource sqlpara=new BeanPropertySqlParameterSource(appUser);
        Integer seq= null;
        try {
            seq = namedParameterJdbcTemplate.queryForObject(sql,sqlpara,Integer.class);
            if(seq!=null && seq.intValue()>0)
                return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
        return false;
    }


    private int getSequence(){
        String sql="SELECT max(APP_USER_CD) FROM VSV58378.APP_USER ";
        Integer seq= null;
        try {
            seq = namedParameterJdbcTemplate.queryForObject(sql,new HashMap(),Integer.class);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return seq==null?0:seq;
    }

    public AppUser updateAppUser(AppUser appuser){
        String sql="UPDATE VSV58378.APP_USER set STATUS=:status ,PASSWORD=:password ,LASTNAME=:lastname ,FIRSTNAME=:firstname ,APP_USER_ROLE_CD=:appuserrolecd ,MOBILENO=:mobileno ,EMAILID=:emailid  WHERE APP_USER_CD = :appusercd ";
        SqlParameterSource sqlpara=new BeanPropertySqlParameterSource(appuser);
        namedParameterJdbcTemplate.update(sql,sqlpara);
        return (AppUser) getAppUserById(appuser.getAppusercd());
    }

    public AppUser deleteAppUser(int appusercd){
        Map<String,Object> map = new HashMap<>(1);
        map.put("appusercd",appusercd);
        String sql="DELETE FROM VSV58378.APP_USER  WHERE APP_USER_CD = :appusercd ";
        int update = namedParameterJdbcTemplate.update(sql,map);
        return update==0?null:APPUSER;
    }
}