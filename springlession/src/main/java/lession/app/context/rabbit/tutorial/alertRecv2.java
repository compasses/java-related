package lession.app.context.rabbit.tutorial;

import com.rabbitmq.client.*;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.*;

/**
 * Created by I311352 on 9/23/2016.
 */
public class alertRecv2 {
    private static final String EXCHANGE_NAME = "logs";
    private static final String CRITICAL_QUEUE = "critical";
    private static final String LIMIT_QUEUE = "rate_limit";

    private static final Logger logger = Logger.getLogger(alertRecv.class);

    static class CriticalConsumer extends DefaultConsumer {
        ExecutorService threadPool = null;
        public CriticalConsumer(Channel channel, final ExecutorService threadPool) {
            super(channel);
            this.threadPool = threadPool;
        }

        public void handleDelivery(String consumerTag, Envelope envelope,
                                   AMQP.BasicProperties properties, byte[] body) throws UnsupportedEncodingException {
            String message = new String(body, "UTF-8");

            logger.info("consumertag: " + consumerTag + " Envelope : " + envelope.toString() +
                    " BasicProperties: " + properties);
            Channel channel = this.getChannel();
            try {
                logger.info(String.format("Received (channel %d) %s", channel.getChannelNumber(), new String(body)));

                threadPool.submit(new Runnable() {
                    public void run() {
                        try {
                            TimeUnit.SECONDS.sleep(3);
                            logger.info(String.format("Processed %s", new String(body)));
                            channel.basicAck(envelope.getDeliveryTag(), false);
                            logger.info(String.format("Sent ACK %s", new String(body)));

                        } catch (InterruptedException e) {
                            logger.warn(String.format("Interrupted %s", new String(body)));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (Exception e) {
                logger.error("", e);
            }
            logger.info(" [x] Critical Received '" + message + "'");
        }
    }
    static class RateLimitConsumer extends DefaultConsumer {
        ExecutorService threadPool = null;

        public RateLimitConsumer(Channel channel, ExecutorService threadPool) {
            super(channel);
            this.threadPool = threadPool;
        }

        public void handleDelivery(String consumerTag, Envelope envelope,
                                   AMQP.BasicProperties properties, byte[] body) throws UnsupportedEncodingException {
            String message = new String(body, "UTF-8");
            logger.info("consumertag: " + consumerTag + " Envelope : " + envelope.toString() +
                    " BasicProperties: " + properties);
            Channel channel = this.getChannel();

            try {
                logger.info(String.format("Received (channel %d) %s", getChannel().getChannelNumber(), new String(body)));

                threadPool.submit(new Runnable() {
                    public void run() {
                        try {
                            TimeUnit.SECONDS.sleep(3);
                            logger.info(String.format("Processed %s", new String(body)));
                            channel.basicAck(envelope.getDeliveryTag(), false);
                            logger.info(String.format("Sent ACK %s", new String(body)));

                        } catch (InterruptedException e) {
                            logger.warn(String.format("Interrupted %s", new String(body)));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (Exception e) {
                logger.error("", e);
            }
            logger.info(" [x] RateLimit Received '" + message + "'");
        }
    }
    public static void main(String[] argv) throws java.io.IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");//10.128.165.206
        connectionFactory.setPort(56672);
        connectionFactory.setUsername("root");
        connectionFactory.setPassword("Initial0");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        int threadNumber = 20;
        final ExecutorService threadPool =  new ThreadPoolExecutor(threadNumber, threadNumber,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());

        //
//        channel.exchangeDeclare(EXCHANGE_NAME, "topic", false);
//        channel.queueDeclare(CRITICAL_QUEUE, false, false, true, null);
//        channel.queueBind(CRITICAL_QUEUE, EXCHANGE_NAME, "critical.#");
//
//        channel.queueDeclare(LIMIT_QUEUE, false, false, true, null);
//        channel.queueBind(LIMIT_QUEUE, EXCHANGE_NAME, "#.rate_limit");


        channel.basicConsume(CRITICAL_QUEUE, false, new alertRecv2.CriticalConsumer(channel, threadPool));
        channel.basicConsume(LIMIT_QUEUE, false, new alertRecv2.RateLimitConsumer(channel, threadPool));
    }
}
