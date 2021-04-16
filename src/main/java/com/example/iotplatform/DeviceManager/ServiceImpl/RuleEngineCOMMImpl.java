package com.example.iotplatform.DeviceManager.ServiceImpl;

import com.example.iotplatform.DeviceManager.PO.DeviceShadow;
import com.example.iotplatform.DeviceManager.Service.DeviceShadowManager;
import com.example.iotplatform.DeviceManager.Service.RuleEngineCOMM;
import com.example.iotplatform.RuleEngine.Service.RuleEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: hyh
 * @Description:
 * @Date: 2021/4/14 10:05 下午
 */
@Service
public class RuleEngineCOMMImpl implements RuleEngineCOMM {

    @Autowired
    DeviceShadowManager deviceShadowManager;

    @Autowired
    RuleEngine ruleEngine;

    @Override
    public void control(int id, String desired) {
        deviceShadowManager.control(id,desired);
    }

    @Override
    public void notifyRuleEngine(int id, String reported) {
        System.out.println("调用rulematch");
        ruleEngine.ruleMatch();
    }


}
