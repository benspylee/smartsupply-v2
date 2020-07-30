package com.bluebee.smartsupply.dao;


import com.bluebee.smartsupply.model.Shop;
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
public class ShopDao extends NamedParameterJdbcDaoSupport {

    static final Shop SHOP=new Shop();

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Shop> getAllShop(){
    String sql="SELECT ADDRESS_3  address3 ,STATUS  status ,OWNER_NAME  ownername ,ADDESS_1  addess1 ,SHOP_NAME  shopname ,ZIPCODE  zipcode ,APP_USER_CD  appusercd ,SHOP_CD  shopcd ,MOBILENO  mobileno ,EMAILID  emailid ,ADDRESS_2  address2  FROM VSV58378.SHOP  WHERE STATUS =  1";
    return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper(Shop.class));
    }

    public Shop getShopById(int shop_cd){
        Map<String,Object> map = new HashMap<>(1);
        map.put("shopcd",shop_cd);
        String sql="SELECT ADDRESS_3  address3 ,STATUS  status ,OWNER_NAME  ownername ,ADDESS_1  addess1 ,SHOP_NAME  shopname ,ZIPCODE  zipcode ,APP_USER_CD  appusercd ,SHOP_CD  shopcd ,MOBILENO  mobileno ,EMAILID  emailid ,ADDRESS_2  address2  FROM VSV58378.SHOP  WHERE SHOP_CD = :shopcd ";
        return (Shop) namedParameterJdbcTemplate.queryForObject(sql,map,new BeanPropertyRowMapper(Shop.class));
    }

    public List<Shop> getShopByCriteria(Shop shop) throws Exception{
        Map<String,Object> map = new HashMap<>(1);
      //  if(!shop.getZipcode().equals(""))
        map.put("zipcode",shop.getZipcode());
       // if(!shop.getZipcode().equals(""))
        map.put("shopname",shop.getShopname());
       // if(!shop.getItemname().equals(""))
        map.put("itemname",shop.getItemname());
        String sql="SELECT distinct a.ADDRESS_3  address3 ,a.STATUS  status ,a.OWNER_NAME  ownername ,a.ADDESS_1  addess1 ,a.SHOP_NAME  shopname ,a.ZIPCODE  zipcode ,a.APP_USER_CD  appusercd ,a.SHOP_CD  shopcd ,a.MOBILENO  mobileno ,a.EMAILID  emailid ,a.ADDRESS_2  address2  FROM VSV58378.SHOP a,VSV58378.ITEM_INFO b WHERE a.APP_USER_CD=  b.APP_USER_CD and (a.ZIPCODE=:zipcode or a.SHOP_NAME  =:shopname or b.ITEMNAME=:itemname)";
        return (List<Shop>) namedParameterJdbcTemplate.query(sql,map,new BeanPropertyRowMapper(Shop.class));
    }

    public List<Shop> getShopByUserId(int appusercd) throws Exception{
        Map<String,Object> map = new HashMap<>(1);
        map.put("appusercd",appusercd);
        String sql="SELECT ADDRESS_3  address3 ,STATUS  status ,OWNER_NAME  ownername ,ADDESS_1  addess1 ,SHOP_NAME  shopname ,ZIPCODE  zipcode ,APP_USER_CD  appusercd ,SHOP_CD  shopcd ,MOBILENO  mobileno ,EMAILID  emailid ,ADDRESS_2  address2  FROM VSV58378.SHOP  WHERE STATUS =  1 and APP_USER_CD  =:appusercd";
        return namedParameterJdbcTemplate.query(sql,map, new BeanPropertyRowMapper(Shop.class));

    }



    public Shop addShop(Shop shop){
        String sql="INSERT INTO VSV58378.SHOP(ADDESS_1,ADDRESS_2,ADDRESS_3,APP_USER_CD,EMAILID,MOBILENO,OWNER_NAME,SHOP_CD,SHOP_NAME,STATUS,ZIPCODE) values(:addess1,:address2,:address3,:appusercd,:emailid,:mobileno,:ownername,:shopcd,:shopname,:status,:zipcode)";
        shop.setShopcd(getSequence()+1);
        SqlParameterSource sqlpara=new BeanPropertySqlParameterSource(shop);
        namedParameterJdbcTemplate.update(sql,sqlpara);
        return getShopById(shop.getShopcd());
    }




    private int getSequence(){
        String sql="SELECT max(SHOP_CD) FROM VSV58378.SHOP ";
        Integer seq= null;
        try {
            seq = namedParameterJdbcTemplate.queryForObject(sql,new HashMap(),Integer.class);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return seq==null?0:seq;
    }

    public Shop updateShop(Shop shop){
        String sql="UPDATE VSV58378.SHOP set ADDRESS_3=:address3 ,STATUS=:status ,OWNER_NAME=:ownername ,ADDESS_1=:addess1 ,SHOP_NAME=:shopname ,ZIPCODE=:zipcode ,APP_USER_CD=:appusercd ,MOBILENO=:mobileno ,EMAILID=:emailid ,ADDRESS_2=:address2  WHERE SHOP_CD = :shopcd ";
        SqlParameterSource sqlpara=new BeanPropertySqlParameterSource(shop);
        namedParameterJdbcTemplate.update(sql,sqlpara);
        return (Shop) getShopById(shop.getShopcd());
    }

    public Shop deleteShop(int shop_cd){
        Map<String,Object> map = new HashMap<>(1);
        map.put("shopcd",shop_cd);
        String sql="DELETE FROM VSV58378.SHOP  WHERE SHOP_CD = :shopcd ";
        int update = namedParameterJdbcTemplate.update(sql,map);
        return update==0?null:SHOP;
    }
}