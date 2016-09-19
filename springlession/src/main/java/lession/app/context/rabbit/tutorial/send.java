package lession.app.context.rabbit.tutorial;

/**
 * Created by i311352 on 9/15/16.
 */

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.concurrent.TimeoutException;

public class send {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws java.io.IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String message = "hello world";
        int i = 0;
        while (true) {
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            message += i++;
            System.out.println("[x] sent '" + message + "'");
        }
//        System.out.println("[x] sent '" + message + "'");
//        channel.close();
//        connection.close();

    }
}
