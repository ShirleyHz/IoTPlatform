package com.example.iotplatform.DeviceManager.Service;

/**
 * @Author: hyh
 * @Description:
 * @Date: 2021/4/14 5:30 下午
 */
public interface DeviceShadowManager {
    //检测该设备影子/设备是否需要更新
        //如果需要就更新，并传给规则引擎
        //如果不需要更新，不需要做变化
    void update(int id,String payload);

    //规则引擎调用
    void control(int id,String payload);

}
