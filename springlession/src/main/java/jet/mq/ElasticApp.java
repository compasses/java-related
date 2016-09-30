package jet.mq;

import com.rabbitmq.client.*;
import jet.mq.elastic.index.IndexService;
import jet.mq.products.ProductService;
import jet.mq.republish.PublishMsg;
import lession.app.context.rabbit.tutorial.alertRecv2;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.*;


/**
 * Created by I311352 on 9/30/2016.
 */
@SpringBootApplication
public class ElasticApp {
    private static final Logger logger = Logger.getLogger(ElasticApp.class);

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

    public static void consumeMsg() throws TimeoutException, IOException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("10.128.165.206");//10.128.165.206
        connectionFactory.setPort(5672);
//        connectionFactory.setUsername("root");
//        connectionFactory.setPassword("Initial0");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        int threadNumber = 20;
        final ExecutorService threadPool =  new ThreadPoolExecutor(threadNumber, threadNumber,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());

        channel.basicConsume(PublishMsg.ELASTICQUEUENAME, false, new ElasticApp.CriticalConsumer(channel, threadPool));
    }

    public static void main(String args[]) {
        ConfigurableApplicationContext context = SpringApplication.run(ElasticApp.class, args);
        IndexService indexService = context.getBean(IndexService.class);
        ProductService productService = context.getBean(ProductService.class);
        if (!indexService.indexExist(ProductService.INDEX)) {
            productService.createIndex();
        }

        try {
            consumeMsg();
        } catch (IOException e) {
            logger.error("Error on consume msg", e);
        } catch (TimeoutException e) {
            logger.error("Error on consume timeout", e);
        }

    }

}

