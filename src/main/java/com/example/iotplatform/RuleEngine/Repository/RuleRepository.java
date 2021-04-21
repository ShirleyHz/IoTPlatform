package com.example.iotplatform.RuleEngine.Repository;

import com.example.iotplatform.DeviceManager.DAO.DeviceDAO;
import com.example.iotplatform.DeviceManager.PO.ConnectionStatus;
import com.example.iotplatform.RuleEngine.Model.Rule;

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
import java.time.LocalDateTime;

/**
 * @Author: hyh
 * @Description:
 * @Date: 2021/4/14 5:37 下午
 */

@Repository("RuleRepository")
public class RuleRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Rule> findAll() {
        String sql = "select * FROM Rule;";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Rule.class));
    }

    public void insert(int salience,LocalDateTime effective_date,LocalDateTime expires_date,boolean enabled,String foreWare,String hindWare) {
        String sql = "insert into Rule value (null,?,?,?,?,?,?);";
        jdbcTemplate.update(sql,salience,effective_date,expires_date,enabled,foreWare,hindWare);
    }

    public void delete(int id) {
        String sql = "delete from Rule where id = ?;";
        jdbcTemplate.update(sql,id);
    }
}
