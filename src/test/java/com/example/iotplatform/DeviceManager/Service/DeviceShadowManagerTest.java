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
 * @Date: 2021/4/15 1:12 上午
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
class DeviceShadowManagerTest {
    @Autowired
    DeviceShadowManager deviceShadowManager;

    @Autowired
    ConnectionCOMM connectionCOMM;

    @Test
    void connect(){
//        deviceShadowManager.control(1,"{\"status\":0,\"temperature\":24,\"mode\":1}");

//        模拟设备离线
//        connectionCOMM.disconnect(1);
//        deviceShadowManager.control(1,"{\"status\":1,\"temperature\":24,\"mode\":1}");

        //模拟设备上线
//        connectionCOMM.connect(1,"{\"status\":0,\"temperature\":24,\"mode\":1}");
    }

}