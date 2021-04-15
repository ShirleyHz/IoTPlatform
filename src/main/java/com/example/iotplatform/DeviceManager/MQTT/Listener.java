package com.example.iotplatform.DeviceManager.MQTT;

/**
 * @Author: hyh
 * @Description:
 * @Date: 2021/4/15 10:49 上午
 */
public interface Listener {
    //    public static void main(String []args) throws Exception {
    //    @Async("taskExecutor")
    void run(String... args);
}
