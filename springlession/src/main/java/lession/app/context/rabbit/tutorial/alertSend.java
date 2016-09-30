package lession.app.context.rabbit.tutorial;

import com.rabbitmq.client.*;
import com.rabbitmq.client.impl.AMQImpl;
import org.springframework.amqp.core.Exchange;

import java.util.concurrent.TimeoutException;

/**
 * Created by I311352 on 9/20/2016.
 */
public class alertSend {
    private static final String EXCHANGE_NAME = "logs";
    private static final String CRITICAL_QUEUE = "critical";
    private static final String LIMIT_QUEUE = "rate_limit";

    public static void main(String[] argv) throws java.io.IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("10.128.165.206");
        connectionFactory.setPort(5672);
//        connectionFactory.setUsername("root");
//        connectionFactory.setPassword("Initial0");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        //
        channel.exchangeDeclare(EXCHANGE_NAME, "topic", false);
        channel.queueDeclare(CRITICAL_QUEUE, false, false, false, null);
        channel.queueBind(CRITICAL_QUEUE, EXCHANGE_NAME, "critical.#");

        channel.queueDeclare(LIMIT_QUEUE, false, false, false, null);
        channel.queueBind(LIMIT_QUEUE, EXCHANGE_NAME, "#.rate_limit");
        for (int i = 0; i < 2;) {
            String message = "Critical happend";
            message += i++;

            channel.basicPublish(EXCHANGE_NAME, "critical.good", null, message.getBytes());
            System.out.println(" [x] Sent '" + "critical.good" + "':'" + message + "'");
            message = "Alert happend";
            message += i++;

            channel.basicPublish(EXCHANGE_NAME, LIMIT_QUEUE, MessageProperties.TEXT_PLAIN, message.getBytes());
            System.out.println(" [x] Sent '" + LIMIT_QUEUE + "':'" + message + "'");

        }


        channel.close();
        connection.close();
    }
}
