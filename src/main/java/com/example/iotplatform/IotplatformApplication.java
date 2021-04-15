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

//    // 声明一个线程池(并指定线程池的名字)
//    @Bean("taskExecutor")
//    public Executor asyncExecutor() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        //核心线程数5：线程池创建时候初始化的线程数
//        executor.setCorePoolSize(5);
//        //最大线程数5：线程池最大的线程数，只有在缓冲队列满了之后才会申请超过核心线程数的线程
//        executor.setMaxPoolSize(5);
//        //缓冲队列500：用来缓冲执行任务的队列
//        executor.setQueueCapacity(500);
//        //允许线程的空闲时间60秒：当超过了核心线程出之外的线程在空闲时间到达之后会被销毁
//        executor.setKeepAliveSeconds(60);
//        //线程池名的前缀：设置好了之后可以方便我们定位处理任务所在的线程池
//        executor.setThreadNamePrefix("DailyAsync-");
//        executor.initialize();
//        return executor;
//    }
}
