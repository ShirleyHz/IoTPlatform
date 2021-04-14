package com.example.iotplatform.DeviceManager.DAO;

/**
 * @Author: hyh
 * @Description:
 * @Date: 2021/4/13 10:44 上午
 */
public interface DeviceShadowDAO {
    boolean contain(int id);
    void insert(int id,String json);
    String getJson(int id);
    void update(int id,String json);
}
