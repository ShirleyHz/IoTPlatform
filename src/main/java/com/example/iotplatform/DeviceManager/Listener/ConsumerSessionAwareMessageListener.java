package com.example.iotplatform.DeviceManager.Listener;


import javax.jms.*;

/**
 * @Author: hyh
 * @Description:
 * @Date: 2021/4/13 11:32 上午
 */
public class ConsumerSessionAwareMessageListener implements MessageListener {
//    public void onMessage(BytesMessage message, Session session) throws JMSException {
//        try {
//            Destination destination = message.getJMSDestination();
//            String topic = destination.toString();
//            long length = message.getBodyLength();
//            byte[] b = new byte[(int) length];
//            message.readBytes(b);
//            System.out.println(topic);
//            if (topic.equals("topic:/sensor/sound")) {
//                System.out.println("t1");
//                //...
//            }else if (topic.equals("topic://topic2")){
//                //...
//                System.out.println("t2");
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }
    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            System.out.println("收到消息："+textMessage.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
