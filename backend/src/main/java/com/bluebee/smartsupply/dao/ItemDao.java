package com.bluebee.smartsupply.dao;


import com.bluebee.smartsupply.model.Items;
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
public class ItemDao extends NamedParameterJdbcDaoSupport {

    static final Items ITEM=new Items();

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Items> getAllItems(){
       return namedParameterJdbcTemplate.query("SELECT a.APP_USER_CD appusercd,a.ITEMNAME,a.ITEMDESC,a.ITEMID,a.PRODCAT,a.ITEMCODE,b.ITEM_CAT_NAME as PRODCATDESC,a.ImageID,a.URL,a.PRICE ,a.UNITCODE FROM VSV58378.ITEM_INFO a,\"VSV58378\".\"ITEM_CATEGORY\" b where a.PRODCAT=b.ITEM_CAT_CD", new BeanPropertyRowMapper(Items.class));
    }

    public Items getItemById(int itemid){
        Map<String,Object> map = new HashMap<>(1);
        map.put("itemid",itemid);
        String sql="SELECT a.APP_USER_CD appusercd,a.ITEMNAME,a.ITEMDESC,a.ITEMID,a.PRODCAT,a.ITEMCODE,b.ITEM_CAT_NAME as PRODCATDESC,a.ImageID,a.URL,a.PRICE ,a.UNITCODE FROM VSV58378.ITEM_INFO a,\"VSV58378\".\"ITEM_CATEGORY\" b where a.PRODCAT=b.ITEM_CAT_CD and a.ITEMCODE= :itemid";
        return (Items) namedParameterJdbcTemplate.queryForObject(sql,map,new BeanPropertyRowMapper(Items.class));
    }

    public  List<Items> getItemByUserId(int appuserid) throws Exception{
        Map<String,Object> map = new HashMap<>(1);
        map.put("appuserid",appuserid);
        String sql="SELECT a.APP_USER_CD appusercd,a.ITEMNAME,a.ITEMDESC,a.ITEMID,a.PRODCAT,a.ITEMCODE,b.ITEM_CAT_NAME as PRODCATDESC,a.ImageID,a.URL,a.PRICE ,a.UNITCODE FROM VSV58378.ITEM_INFO a,\"VSV58378\".\"ITEM_CATEGORY\" b where a.PRODCAT=b.ITEM_CAT_CD and a.APP_USER_CD= :appuserid";
        return (List<Items>) namedParameterJdbcTemplate.query(sql,map,new BeanPropertyRowMapper(Items.class));
    }

    public Items addItem(Items item){
        String sql="insert into VSV58378.ITEM_INFO (ITEMNAME,ITEMDESC,ITEMID,PRODCAT,ITEMCODE,IMAGEID,URL,PRICE ,UNITCODE,APP_USER_CD) " +
                "values(:itemname,:itemdesc,:itemId,:prodcat,:itemcode,:imageid,:url,:price,:unitcode,:appusercd)";
        item.setItemcode(getSequence()+1);
        String stage=null;
        stage = getProdCat(item);
        item.setItemId(stage);
        SqlParameterSource sqlpara=new BeanPropertySqlParameterSource(item);
        namedParameterJdbcTemplate.update(sql,sqlpara);
        return getItemById(item.getItemcode());
    }

    private String getProdCat(Items item) {
        String stage=null;
        if(item.getProdcat()==1){
            stage ="GROW"+"0000"+ item.getItemcode();
        }else if(item.getProdcat()==2){
            stage ="VEG"+"0000"+ item.getItemcode();
        }
        return stage;
    }

    private int getSequence(){
        String sql="SELECT max(ITEMCODE) FROM VSV58378.ITEM_INFO";
        Integer seq= null;
        try {
            seq = namedParameterJdbcTemplate.queryForObject(sql,new HashMap(),Integer.class);
            ;
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return seq==null?0:seq;
    }

    public Items updateItem(Items item){
        String stage=null;
        stage = getProdCat(item);
        item.setItemId(stage);
        //:itemname,:itemdesc,:itemId,:prodcat,:itemcode
        String sql="UPDATE  VSV58378.ITEM_INFO set ITEMNAME=:itemname,ITEMDESC=:itemdesc,ITEMID=:itemId,PRODCAT=:prodcat,IMAGEID=:imageid,URL=:url,PRICE=:price,UNITCODE=:unitcode,APP_USER_CD=:appusercd where ITEMCODE = :itemcode";
        SqlParameterSource sqlpara=new BeanPropertySqlParameterSource(item);
        namedParameterJdbcTemplate.update(sql,sqlpara);
        return (Items) getItemById(item.getItemcode());
    }

    public Items deleteItem(int itemid){
        Map<String,Object> map = new HashMap<>(1);
        map.put("itemid",itemid);
        String sql="DELETE FROM VSV58378.ITEM_INFO where ITEMCODE= :itemid";
        int update = namedParameterJdbcTemplate.update(sql,map);
        return update==0?null:ITEM;
    }
}
