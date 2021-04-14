package com.example.iotplatform.DeviceManager.DAOImpl;

import com.example.iotplatform.DeviceManager.DAO.DeviceDAO;
import com.example.iotplatform.DeviceManager.PO.ConnectionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @Author: hyh
 * @Description:
 * @Date: 2021/4/14 5:37 下午
 */

@Repository("DeviceDAO")
public class DeviceDAOImpl implements DeviceDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public boolean contain(int deviceId) {
        String sql = "select * FROM ConnectionStatus where deviceId=?;";
       try {
           jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(ConnectionStatus.class),deviceId);
           System.out.println(deviceId+"contain");
           return true;
       }catch (EmptyResultDataAccessException e){
           return false;
       }
    }

    @Override
    public boolean isConnect(int deviceId) {
        String sql = "select connectionStatus FROM ConnectionStatus where deviceId=?;";
        Boolean result = jdbcTemplate.queryForObject(sql,Boolean.class,deviceId);
        return result;
    }

    @Override
    public void insert(int deviceId) {
        String sql = "insert into ConnectionStatus value (null,?,?);";
        jdbcTemplate.update(sql,deviceId,true);
    }

    @Override
    public void modify(int deviceId, boolean status) {
        String sql = "update ConnectionStatus set connectionStatus=? where deviceId=?;";
        jdbcTemplate.update(sql,status,deviceId);
    }

}
