package com.example.iotplatform;

import com.example.iotplatform.DeviceManager.DAO.DeviceDAO;
import com.example.iotplatform.DeviceManager.Service.ConnectionCOMM;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author: hyh
 * @Description:
 * @Date: 2021/4/14 11:44 下午
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class DeviceDAOTest {
    @Autowired
    ConnectionCOMM connectionCOMM;

    @Autowired
    DeviceDAO deviceDAO;

    @Autowired
    JdbcTemplate jdbcTemplate;


    @Test
    public void connect(){
//        System.out.println(jdbcTemplate);
        System.out.println(deviceDAO.contain(1));;
        deviceDAO.insert(1);
        System.out.println(deviceDAO.isConnect(1));;
        deviceDAO.modify(1,false);
        System.out.println(deviceDAO.isConnect(1));;
    }
}
