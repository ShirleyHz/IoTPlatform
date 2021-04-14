package com.example.iotplatform.DeviceManager.PO;

/**
 * @Author: hyh
 * @Description:
 * @Date: 2021/4/14 9:02 下午
 */
public class DeviceShadow {
    int deviceId;
    String shadow;

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getShadow() {
        return shadow;
    }

    public void setShadow(String shadow) {
        this.shadow = shadow;
    }
}
