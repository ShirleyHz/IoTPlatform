package com.example.iotplatform.Controller;

import com.example.iotplatform.DeviceManager.MQTT.Listener;
import com.example.iotplatform.DeviceManager.Service.ConnectionCOMM;
import com.example.iotplatform.DeviceManager.Service.DeviceShadowManager;
import com.example.iotplatform.DeviceManager.Service.RuleEngineCOMM;
import com.example.iotplatform.RuleEngine.Service.RuleEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: hyh
 * @Description:
 * @Date: 2021/4/11 4:40 下午
 */
@RestController
public class TestController {

    @Autowired
    Listener listener;
    @Autowired
    RuleEngineCOMM ruleEngineCOMM;
    @Autowired
    ConnectionCOMM connectionCOMM;

    @Autowired
    DeviceShadowManager deviceShadowManager;

    @Autowired
    RuleEngine ruleEngine;

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String helloSpringBoot(){
//        listener.run();
//        connectionCOMM.disconnect(1);
//        ruleEngineCOMM.control(1,"{\"status\":1,\"temperature\":24,\"mode\":0}");
//        connectionCOMM.connect(1,"{\"status\":0,\"temperature\":24,\"mode\":0}");
//        connectionCOMM.update(3,"{\"temperature\":30}");
//        connectionCOMM.disconnect(1);
//        connectionCOMM.update(3,"{\"temperature\":66}");
//        deviceShadowManager.control(1,"{\"mode\":2,\"temperature\":5}");
        connectionCOMM.connect(1);
        connectionCOMM.disconnect(1);
        connectionCOMM.update(3,"{\"temperature\":66}");
        connectionCOMM.connect(1);
        return "Test!";
    }



}
