package com.example.iotplatform.DeviceManager.Service;

/**
 * @Author: hyh
 * @Description:
 * @Date: 2021/4/13 10:42 上午
 */
public interface ConnectionCOMM {
    void connect(int id,String payload);//连接，保存数据
    void disconnect(int id);//断开连接
    void update(int id,String payload);//设备上传状态，更改设备影子
    void control(int id,String payload);//控制设备状态
    boolean isConnect(int deviceId);//获取设备连接情况
}
