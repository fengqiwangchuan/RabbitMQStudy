package com.njucm.rabbitmq.simple;

import com.njucm.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class Recv {
    private final static String QUEUE_NAME = "simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//                String msg = new String(body);
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                Map<String, String> msg = null;
                try {
                    msg = (Map<String, String>) objectInputStream.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                System.out.println(" [x] received: " + msg + "!");
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        // true 自动ack false 手动ack
        channel.basicConsume(QUEUE_NAME, false, consumer);
    }
}
