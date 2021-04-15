package com.example.iotplatform.DeviceManager.MQTT;

/**
 * @Author: hyh
 * @Description:
 * @Date: 2021/4/15 10:49 上午
 */
public interface Publisher {
    void publish(int deviceId, String payload) throws Exception;
}
