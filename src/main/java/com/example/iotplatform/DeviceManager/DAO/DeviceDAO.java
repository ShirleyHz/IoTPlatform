package com.example.iotplatform.DeviceManager.DAO;

import org.springframework.stereotype.Repository;

/**
 * @Author: hyh
 * @Description:
 * @Date: 2021/4/13 10:44 上午
 */
public interface DeviceDAO {
    boolean contain(int deviceId);
    boolean isConnect(int deviceId);
    void insert(int deviceId);
    void modify(int deviceId,boolean status);
}
