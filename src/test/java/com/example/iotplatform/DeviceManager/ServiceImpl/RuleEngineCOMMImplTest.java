package com.example.iotplatform.DeviceManager.ServiceImpl;

import com.example.iotplatform.DeviceManager.MQTT.Listener;
import com.example.iotplatform.DeviceManager.Service.ConnectionCOMM;
import com.example.iotplatform.DeviceManager.Service.RuleEngineCOMM;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author: hyh
 * @Description:
 * @Date: 2021/4/15 12:39 下午
 */

@RunWith(SpringRunner.class)
@SpringBootTest
//@RunWith(SpringJUnit4ClassRunner.class)
class RuleEngineCOMMImplTest {

    @Autowired
    RuleEngineCOMM ruleEngineCOMM;
    @Autowired
    ConnectionCOMM connectionCOMM;
    @Autowired
    Listener listener;


    @Test
    void control() {
//        ruleEngineCOMM.control(1,"{\"status\":0,\"temperature\":24,\"mode\":0}");
//        ruleEngineCOMM.control(2,"\"status\":0");
    }

    @Test
    void notifyRuleEngine() {
        ruleEngineCOMM.notifyRuleEngine(3,"{}");
    }

    @Test
    void test1(){ //断开1 规则引擎control1 连接1 1状态改变
                connectionCOMM.disconnect(1);
                ruleEngineCOMM.control(1,"{\"status\":1,\"temperature\":24,\"mode\":0}");
//                connectionCOMM.connect(1,"{\"status\":0,\"temperature\":24,\"mode\":0}");

    }
}