package com.example.iotplatform.DeviceManager.ServiceImpl;

import com.example.iotplatform.DeviceManager.DAO.DeviceDAO;
import com.example.iotplatform.DeviceManager.Service.ConnectionCOMM;
import com.example.iotplatform.DeviceManager.Service.DeviceShadowManager;
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

    @Override
    public void connect(int id,String payload) {
        if(deviceDAO.contain(id)){
            deviceDAO.modify(id,true);
        }else {
            deviceDAO.insert(id);
        }
        //判断该设备是否需要更新
        update(id,payload);
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
        System.out.println("控制设备"+id+payload);
    }

    @Override
    public boolean isConnect(int deviceId) {
        return deviceDAO.isConnect(deviceId);
    }

}
