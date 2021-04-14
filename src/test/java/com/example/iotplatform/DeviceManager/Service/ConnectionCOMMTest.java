package com.example.iotplatform.DeviceManager.Service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author: hyh
 * @Description:
 * @Date: 2021/4/14 11:47 下午
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
class ConnectionCOMMTest {
    @Autowired
    ConnectionCOMM connectionCOMM;

    @Test
    void connect() {
        connectionCOMM.connect(1,"{\"status\":1,\"temperature\":24,\"mode\":1}");
        connectionCOMM.connect(2,"{\"status\":0}");
    }

    @Test
    void disconnect() {
        connectionCOMM.disconnect(2);
    }

    @Test
    void update() {//connect测试
    }

    @Test
    void control() {

    }

    @Test
    void isConnect() {

    }
}