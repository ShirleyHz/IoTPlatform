package com.example.iotplatform.DeviceManager.DAOImpl;

import com.example.iotplatform.DeviceManager.DAO.DeviceShadowDAO;
import com.example.iotplatform.DeviceManager.PO.ConnectionStatus;
import com.example.iotplatform.DeviceManager.PO.DeviceShadow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @Author: hyh
 * @Description:
 * @Date: 2021/4/14 9:01 下午
 */
@Repository("DeviceShadowDAO")
public class DeviceShadowDAOImpl implements DeviceShadowDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean contain(int id) {
        String sql = "select * FROM DeviceShadow where deviceId=?;";
        try {
            jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(DeviceShadow.class),id);
            return true;
        }catch (EmptyResultDataAccessException e){
            return false;
        }
    }

    @Override
    public void insert(int id, String json) {
        String sql = "insert into DeviceShadow value (null,?,?);";
        jdbcTemplate.update(sql,id,json);
    }

    @Override
    public String getJson(int id) {
        String sql = "select shadow FROM DeviceShadow where deviceId=?;";
        String result = jdbcTemplate.queryForObject(sql,String.class,id);
        return result;
    }

    @Override
    public void update(int id, String json) {
        System.out.println("update");
        String sql = "update DeviceShadow set shadow=? where deviceId=?;";
        jdbcTemplate.update(sql,json,id);
    }
}
