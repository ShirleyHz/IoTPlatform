package com.example.iotplatform.DeviceManager.PO;

/**
 * @Author: hyh
 * @Description:
 * @Date: 2021/4/14 6:27 下午
 */
public class ConnectionStatus {
    int id;
    int deviceId;
    boolean connectionStatus;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public boolean getConnectionStatus(){
        return connectionStatus;
    }

    public void setConnectionStatus(boolean connectionStatus) {
        this.connectionStatus = connectionStatus;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

}
