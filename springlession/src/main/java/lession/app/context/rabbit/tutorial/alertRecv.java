package lession.app.context.rabbit.tutorial;

import com.rabbitmq.client.*;
import org.apache.log4j.*;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeoutException;

/**
 * Created by I311352 on 9/20/2016.
 */
public class alertRecv {
    private static final String EXCHANGE_NAME = "logs";
    private static final String CRITICAL_QUEUE = "critical";
    private static final String LIMIT_QUEUE = "rate_limit";

    private static final Logger logger = Logger.getLogger(alertRecv.class);

    static class CriticalConsumer extends DefaultConsumer {
        Channel thisChannel;
        public CriticalConsumer(Channel channel) {
            super(channel);
            thisChannel = channel;
        }

        public void handleDelivery(String consumerTag, Envelope envelope,
                                   AMQP.BasicProperties properties, byte[] body) throws UnsupportedEncodingException {
            String message = new String(body, "UTF-8");
//            logger.info("consumertag: " + consumerTag + " Envelope : " + envelope.toString() +
//            " BasicProperties: " + properties);
            //thisChannel.basicAck(envelope.getDeliveryTag(), false);
            logger.info(" [x] Critical Received '" + message + "'");
            try {
                this.getChannel().basicAck(envelope.getDeliveryTag(), false);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    static class RateLimitConsumer extends DefaultConsumer {
        public RateLimitConsumer(Channel channel) {
            super(channel);
        }

        public void handleDelivery(String consumerTag, Envelope envelope,
                                   AMQP.BasicProperties properties, byte[] body) throws UnsupportedEncodingException {
            String message = new String(body, "UTF-8");
//            logger.info("consumertag: " + consumerTag + " Envelope : " + envelope.toString() +
//                    " BasicProperties: " + properties);

            logger.info(" [x] Critical Received '" + message + "'");
            try {
                this.getChannel().basicAck(envelope.getDeliveryTag(), false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] argv) throws java.io.IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("10.128.165.206");//10.128.165.206
        connectionFactory.setPort(5672);
//        connectionFactory.setUsername("test");
//        connectionFactory.setPassword("test");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        //
//        channel.exchangeDeclare(EXCHANGE_NAME, "topic", false);
//        channel.queueDeclare(CRITICAL_QUEUE, false, false, true, null);
//        channel.queueBind(CRITICAL_QUEUE, EXCHANGE_NAME, "critical.#");
//
//        channel.queueDeclare(LIMIT_QUEUE, false, false, true, null);
//        channel.queueBind(LIMIT_QUEUE, EXCHANGE_NAME, "#.rate_limit");


        channel.basicConsume(CRITICAL_QUEUE, false, new CriticalConsumer(channel));
        channel.basicConsume(LIMIT_QUEUE, false, new RateLimitConsumer(channel));
    }
}
