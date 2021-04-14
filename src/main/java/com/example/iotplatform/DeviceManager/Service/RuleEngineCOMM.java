package com.example.iotplatform.DeviceManager.Service;

/**
 * @Author: hyh
 * @Description:
 * @Date: 2021/4/13 10:43 上午
 */
public interface RuleEngineCOMM {
    void control(int id,String desired);//规则引擎调用这个
    void notifyRuleEngine(int id,String reported);
}
