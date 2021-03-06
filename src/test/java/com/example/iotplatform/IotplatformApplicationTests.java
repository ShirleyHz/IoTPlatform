package com.example.iotplatform;

import com.example.iotplatform.DeviceManager.DAO.DeviceDAO;
import com.example.iotplatform.DeviceManager.DAOImpl.DeviceDAOImpl;
import com.example.iotplatform.DeviceManager.Service.ConnectionCOMM;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
class IotplatformApplicationTests {

    @Autowired
    ConnectionCOMM connectionCOMM;

    @Autowired
    DeviceDAO deviceDAO;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    void contextLoads() {
    }




}
