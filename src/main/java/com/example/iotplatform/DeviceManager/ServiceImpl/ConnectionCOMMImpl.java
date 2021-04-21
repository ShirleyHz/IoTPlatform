package com.example.iotplatform.DeviceManager.ServiceImpl;

import com.example.iotplatform.DeviceManager.DAO.DeviceDAO;
import com.example.iotplatform.DeviceManager.DAO.DeviceShadowDAO;
import com.example.iotplatform.DeviceManager.MQTT.Publisher;
import com.example.iotplatform.DeviceManager.PO.DeviceShadow;
import com.example.iotplatform.DeviceManager.Service.ConnectionCOMM;
import com.example.iotplatform.DeviceManager.Service.DeviceShadowManager;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.org.apache.xerces.internal.dom.CommentImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * @Author: hyh
 * @Description:
 * @Date: 2021/4/14 4:50 下午
 */
@Service
public class ConnectionCOMMImpl implements ConnectionCOMM {

    @Autowired
    DeviceDAO deviceDAO;
    @Autowired
    ConnectionCOMM connectionCOMM;
    @Autowired
    DeviceShadowManager deviceShadowManager;
    @Autowired
    DeviceShadowDAO deviceShadowDAO;

    @Autowired
    Publisher publisher;

    @Override
    public void connect(int id) {
        if(deviceDAO.contain(id)){
            deviceDAO.modify(id,true);
            //判断影子文件是否发现有desire，如果有更新设备状态
            if(deviceShadowDAO.contain(id)){//存在
                String json=deviceShadowDAO.getJson(id);
                System.out.println(json);
                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(json);
                JsonObject object = element.getAsJsonObject();
                JsonObject state = object.getAsJsonObject("State");

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
                }
            }
        }else {
            deviceDAO.insert(id);
        }
    }

    @Override
    public void disconnect(int id) {
        deviceDAO.modify(id,false);
    }

    @Override
    public void update(int id,String payload) {//设备上传状态，更改设备影子
        deviceShadowManager.update(id,payload);
    }

    @Override
    public void control(int id,String payload) {
        System.out.println("==============控制设备"+id+payload);
        try {
            publisher.publish(id,payload);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isConnect(int deviceId) {
        return deviceDAO.isConnect(deviceId);
    }

}
