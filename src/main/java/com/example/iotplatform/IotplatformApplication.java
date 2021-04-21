package com.example.iotplatform;

import com.example.iotplatform.DeviceManager.MQTT.Listener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
public class IotplatformApplication implements AsyncConfigurer {
    public static void main(String[] args) {
        SpringApplication.run(IotplatformApplication.class, args);
    }
    

}
