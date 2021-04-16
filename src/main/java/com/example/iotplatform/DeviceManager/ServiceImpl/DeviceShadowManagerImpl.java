package com.example.iotplatform.DeviceManager.ServiceImpl;

import com.example.iotplatform.DeviceManager.DAO.DeviceShadowDAO;
import com.example.iotplatform.DeviceManager.Service.ConnectionCOMM;
import com.example.iotplatform.DeviceManager.Service.DeviceShadowManager;
import com.example.iotplatform.DeviceManager.Service.RuleEngineCOMM;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author: hyh
 * @Description:
 * @Date: 2021/4/14 8:12 下午
 */
@Service
public class DeviceShadowManagerImpl implements DeviceShadowManager {

    @Autowired
    DeviceShadowDAO deviceShadowDAO;

    @Autowired
    ConnectionCOMM connectionCOMM;

    @Autowired
    RuleEngineCOMM ruleEngineCOMM;

    @Override
    public void update(int id, String payload) { //payload是desire:  {} //连接/状态更新时候用
        //检测该设备影子是否需要更新(不存在需要新建)
        //先判断影子文件是否发现有desire，如果有更新设备状态
        //没有的话判断reported和现在的设备状态是否一样,不同要更新shadow
            //如果需要就更新，并传给规则引擎
            //如果不需要更新，不需要做变化
        //存在吗
        System.out.println(deviceShadowDAO.contain(id));
        if(deviceShadowDAO.contain(id)){//存在
            //先判断影子文件是否发现有desire，如果有更新设备状态
            String json=deviceShadowDAO.getJson(id);
            System.out.println(json);
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(json);
            JsonObject object = element.getAsJsonObject();
            JsonObject state = object.getAsJsonObject("State");
            System.out.println(state.toString());

            JsonObject desired = state.getAsJsonObject("desired");
            System.out.println("desired:" + desired.toString());
            if (desired.toString().length()!=2){
                //控制设备状态
                connectionCOMM.control(id,desired.toString());
                state.add("reported",desired);
                state.add("desired",parser.parse("{}").getAsJsonObject());
                System.out.println(state.getAsJsonObject("desired").toString());
                object.add("State",state);
                //desire删除
                deviceShadowDAO.update(id,object.toString());
            }else {
                JsonObject reported = state.getAsJsonObject("reported");

//                for(Map.Entry<String, JsonElement> type : reported.entrySet()){
//                    JsonObject key = reported.getAsJsonObject(type.getKey());
//                    JsonObject value=reported.ge
//                }

//                reported.addProperty("temperature",Integer.parseInt(payload));
//                state.add("reported",reported);
//                object.add("State",state);
//                deviceShadowDAO.update(id,object.toString());
//                //通知规则引擎
//                ruleEngineCOMM.notifyRuleEngine(id,payload);

                if(!reported.toString().equals(payload)){ //需要更改
                    state.add ("reported",parser.parse(payload).getAsJsonObject());
                    object.add("State",state);
                    deviceShadowDAO.update(id,object.toString());
                    //通知规则引擎
                    System.out.println("通知规则引擎");
                    ruleEngineCOMM.notifyRuleEngine(id,payload);
                }
            }
        }else {
            //不存在-新建
            String s="{\"State\":{\"reported\":"+payload+",\"desired\":{}},\"timestamp\":0,\"version\":0}";
            deviceShadowDAO.insert(id,s);
        }
    }

    @Override
    public void control(int id, String payload) { //规则引擎调用 payload:desired {}
        if(deviceShadowDAO.contain(id)){//存在
            String json=deviceShadowDAO.getJson(id);
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(json);
            JsonObject object = element.getAsJsonObject();  // 转化为对象
            JsonObject state = object.getAsJsonObject("State");

            JsonObject reported = state.getAsJsonObject("reported");
            JsonObject payloadObject=parser.parse(payload).getAsJsonObject();

            boolean change=false;
            for(Map.Entry<String,JsonElement> e:payloadObject.entrySet()){
                String key=e.getKey();
                if(!reported.get(key).toString().equals(e.getValue().toString())){
                    change=true;
                    reported.add(key,e.getValue());
                }
            }
            if(change){ //需要更改
                System.out.println("需要control设备");
                if(connectionCOMM.isConnect(id)){//如果设备处于连接状态 control
                    connectionCOMM.control(id,reported.toString());
                    state.add("reported",reported);
                }else {
                    state.add("desired",reported);
                }
                object.add("State",state);
                deviceShadowDAO.update(id,object.toString());
            }
        }else {
            //不存在-新建
            String s="{\"State\":{\"reported\":{},\"desired\":"+payload+"},\"timestamp\":0,\"version\":0}";
            deviceShadowDAO.insert(id,s);
        }
    }
}
