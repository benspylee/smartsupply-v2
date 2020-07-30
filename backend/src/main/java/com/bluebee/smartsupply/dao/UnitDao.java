package com.bluebee.smartsupply.dao;


import com.bluebee.smartsupply.model.Unit;
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
public class UnitDao extends NamedParameterJdbcDaoSupport {

    static final Unit UNIT=new Unit();

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Unit> getAllUnit(){
    String sql="SELECT UNITNAME  unitname ,UNIT_DESC  unitdesc ,STATUS  status ,UNITCODE  unitcode  FROM VSV58378.UNIT  WHERE STATUS =  1";
    return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper(Unit.class));
    }

    public Unit getUnitById(int unitcode){
        Map<String,Object> map = new HashMap<>(1);
        map.put("unitcode",unitcode);
        String sql="SELECT UNITNAME  unitname ,UNIT_DESC  unitdesc ,STATUS  status ,UNITCODE  unitcode  FROM VSV58378.UNIT  WHERE UNITCODE = :unitcode ";
        return (Unit) namedParameterJdbcTemplate.queryForObject(sql,map,new BeanPropertyRowMapper(Unit.class));
    }

    public Unit addUnit(Unit unit){
        String sql="INSERT INTO VSV58378.UNIT(STATUS,UNITCODE,UNITNAME,UNIT_DESC) values(:status,:unitcode,:unitdesc,:unitname)";
        unit.setUnitcode(getSequence()+1);
        SqlParameterSource sqlpara=new BeanPropertySqlParameterSource(unit);
        namedParameterJdbcTemplate.update(sql,sqlpara);
        return getUnitById(unit.getUnitcode());
    }


    private int getSequence(){
        String sql="SELECT max(UNITCODE) FROM VSV58378.UNIT ";
        Integer seq= null;
        try {
            seq = namedParameterJdbcTemplate.queryForObject(sql,new HashMap(),Integer.class);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return seq==null?0:seq;
    }

    public Unit updateUnit(Unit unit){
        String sql="UPDATE VSV58378.UNIT set UNITNAME=:unitname ,UNIT_DESC=:unitdesc ,STATUS=:status  WHERE UNITCODE = :unitcode ";
        SqlParameterSource sqlpara=new BeanPropertySqlParameterSource(unit);
        namedParameterJdbcTemplate.update(sql,sqlpara);
        return (Unit) getUnitById(unit.getUnitcode());
    }

    public Unit deleteUnit(int unitcode){
        Map<String,Object> map = new HashMap<>(1);
        map.put("unitcode",unitcode);
        String sql="DELETE FROM VSV58378.UNIT  WHERE UNITCODE = :unitcode ";
        int update = namedParameterJdbcTemplate.update(sql,map);
        return update==0?null:UNIT;
    }
}