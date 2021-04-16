/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.iotplatform.DeviceManager.MQTTImpl;

import com.example.iotplatform.DeviceManager.Service.ConnectionCOMM;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.fusesource.hawtbuf.Buffer;
import org.fusesource.hawtbuf.UTF8Buffer;
import org.fusesource.mqtt.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.URISyntaxException;

/**
 * Uses an callback based interface to MQTT.  Callback based interfaces
 * are harder to use but are slightly more efficient.
 */
@Component
@Order(1)
@Service
class ListenerImpl implements CommandLineRunner,com.example.iotplatform.DeviceManager.MQTT.Listener{ //CommandLineRunner ,
//class ListenerImpl implements com.example.iotplatform.DeviceManager.MQTT.Listener{ //CommandLineRunner ,

    @Autowired
    ConnectionCOMM connectionCOMM;

//    public static void main(String []args) throws Exception {
//    @Async("taskExecutor")
    @Async
    @Override
    public void run(String... args){
        System.out.println("run");
        String user = env("ACTIVEMQ_USER", "admin");
        String password = env("ACTIVEMQ_PASSWORD", "admin");
        String host = env("ACTIVEMQ_HOST", "192.168.43.179");
//        String host = env("ACTIVEMQ_HOST", "localhost");
        int port = Integer.parseInt(env("ACTIVEMQ_PORT", "1883"));

//        String[] topicStr={"/homeApplication/airConditional","/homeApplication/electricLight","/sensor/temperature","/sensor/humidity","/sensor/light","/sensor/sound"};
        String[] topicStr={"/sensor/temperature","/sensor/humidity","/sensor/light","/sensor/sound"};
        String[] destinations=new String[topicStr.length];
        for(int i=0;i<topicStr.length;i++){
//            destinations[i]=arg(args,i,topicStr[i]);
            destinations[i]=topicStr[i];
        }

        MQTT mqtt = new MQTT();
        try {
            mqtt.setHost(host, port);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        mqtt.setUserName(user);
        mqtt.setPassword(password);

        final CallbackConnection connection = mqtt.callbackConnection();
        connection.listener(new org.fusesource.mqtt.client.Listener() {
            long count = 0;
            long start = System.currentTimeMillis();

            public void onConnected() {
                System.out.println("监听到连接成功");
                //表示监听到连接成功
                //onConnected表示监听到连接建立，该方法只在建立连接成功时执行一次，表示连接成功建立，如果有必要可以在该方法中进行相应的订阅操作；
            }
            public void onDisconnected() {
                System.out.println("监听到断开连接");
                //表示监听到断开连接
                //onDisconnected表示监听到连接断开，该方法只在断开连接时执行一次，如有必要可以进行相应的资源回收操作。

            }
            public void onFailure(Throwable value) {
                //表示监听失败
                //onFailure表示监听失败，这里可以调用相应的断开连接等方法；
                value.printStackTrace();
                System.exit(-2);
            }
            public void onPublish(UTF8Buffer topic, Buffer msg, Runnable ack) {
                //表示监听成功
                //onPublish表示成功，可以获取到订阅的主题和订阅的内容（UTF8Buffer topicmsg表示订阅的主题, Buffer msg表示订阅的内容），一般的可以在这个方法中获取到订阅的主题和内容然后进行相应的判断和其他业务逻辑操作；
//                System.out.println("监听成功，topic："+topic+" msg:"+msg);
//                System.out.println(msg.getClass());
                String body = msg.utf8().toString();
//                System.out.println(body.getClass());
                System.out.println("监听成功，topic："+topic+" body:"+body);
                if( "SHUTDOWN".equals(body)) {
                    long diff = System.currentTimeMillis() - start;
                    System.out.println(String.format("Received %d in %.2f seconds", count, (1.0*diff/1000.0)));
                    connection.disconnect(new Callback<Void>() {
                        //disconnect表示断开与代理服务器的连接，调用该方法后只是表示断开连接但是实际的connect依然存在并没有为null，再次调用connect方法又能够连接成功。
                        @Override
                        public void onSuccess(Void value) {
                            System.out.println("topic:"+topic+"与服务器断开连接成功");
                            //与服务器断开连接成功
                            System.exit(0);
                        }
                        @Override
                        public void onFailure(Throwable value) {
                            //与服务器断开连接失败
                            System.out.println("topic:"+topic+"与服务器断开连接失败");
                            value.printStackTrace();
                            System.exit(-2);
                        }
                    });
                } else {
                    int deviceId=-1;
                    for(int i=0;i<topicStr.length;i++){
                        if(topicStr[i].equals(topic.toString())){
                            deviceId=i+3;
                            break;
                        }
                    }
                    System.out.println("deviceId:"+deviceId);

//                    connectionCOMM.update(deviceId,body);

                    JsonParser parser = new JsonParser();
                    JsonElement element = parser.parse(body);
                    JsonObject object = element.getAsJsonObject();  // 转化为对象
                    String method = object.get("method").getAsString();
                    String payload=object.get("payload").toString();

                    if(method.equals("connect")){
                        connectionCOMM.connect(deviceId,payload);
                    }else if(method.equals("disconnect")){
                        connectionCOMM.disconnect(deviceId);
                    }else if(method.equals("update")){
                        connectionCOMM.update(deviceId,payload);
                    }else {
                        System.out.println("无法识别的method:"+method);
                    }

                    if( count == 0 ) {
                        start = System.currentTimeMillis();
                    }
                    else if( count % 1000 == 0 ) {
                        System.out.println(String.format("Received %d messages.", count));
                    }
                    count ++;
                }
                ack.run();
            }
        });
        connection.connect(new Callback<Void>() {
            @Override
            public void onSuccess(Void value) {
                System.out.println("连接成功");
                //进入该方法表示连接成功连接成功
                //一般可以在connect的onSuccess方法中发布或者订阅相应的主题，在其onFailure方法中作相应的断开连接等操作

                Topic[] topics =new Topic[destinations.length];
                for(int i=0;i<destinations.length;i++){
                    topics[i]=new Topic(destinations[i], QoS.AT_LEAST_ONCE);
                }
                connection.subscribe(topics, new Callback<byte[]>() {
                    public void onSuccess(byte[] qoses) {
                        //主题订阅成功
                        System.out.println("订阅主题成功："+topics.length);
                    }
                    public void onFailure(Throwable value) {
                        //状态主题订阅失败
                        System.out.println("订阅主题失败："+topics.length);
                        value.printStackTrace();
                        System.exit(-2);
                    }
                });
            }
            @Override
            public void onFailure(Throwable value) {
                //进入该方法表示连接失败
                System.out.println("连接失败");
                value.printStackTrace();
                System.exit(-2);
            }
        });


        // Wait forever..
        synchronized (ListenerImpl.class) {
            while(true) {
                try {
                    ListenerImpl.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static String env(String key, String defaultValue) {
        String rc = System.getenv(key);
        if( rc== null )
            return defaultValue;
        return rc;
    }

    private static String arg(String []args, int index, String defaultValue) {
        if( index < args.length )
            return args[index];
        else
            return defaultValue;
    }

//    @Override
//    public void run(String... args) throws Exception {
//
//    }
}