package com.njucm.rabbitmq.simple;

import com.njucm.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class Send {

    private final static String QUEUE_NAME = "simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        // 创建通道
        Channel channel = connection.createChannel();
        // 声明队列 把消息发送到队列
        // 队列是幂等的   当它不存在才会被创建
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 消息内容
        String message = "hello world";
        Map<String, String> msg = new HashMap<>();
        msg.put("name", "123");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(msg);
//        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        channel.basicPublish("", QUEUE_NAME, null, byteArrayOutputStream.toByteArray());
//        System.out.println(" [x] Sent '" + message + "'");
        System.out.println(" [x] Sent '" + msg + "'");
        channel.close();
        connection.close();
    }
}
