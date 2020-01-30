package com.njucm.rabbitmq.direct;

import com.njucm.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {
    private final static String EXCHANGE_NAME = "direct_exchange";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        String message_1 = "商品新增了, id = 1001";
        String message_2 = "商品修改了, id = 1001";
        // routing key为：insert
        channel.basicPublish(EXCHANGE_NAME, "insert", null, message_1.getBytes());
        channel.basicPublish(EXCHANGE_NAME, "update", null, message_2.getBytes());
        System.out.println(" [商品服务：] Sent '" + message_1 + "'");
        System.out.println(" [商品服务：] Sent '" + message_2 + "'");
        channel.close();
        connection.close();
    }
}
