package jet.mq.products;

import com.rabbitmq.client.*;
import jet.mq.ElasticApp;
import jet.mq.elastic.document.DocumentService;
import jet.mq.elastic.index.IndexService;
import jet.mq.republish.PublishMsg;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

import javax.print.Doc;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.concurrent.*;

/**
 * Created by I311352 on 9/30/2016.
 */
@Service
public class ProductService {
    private static Logger logger = Logger.getLogger(ProductService.class);
    public static final String INDEX = "onlinestore";
    private static final String TYPE = "product";

    private final IndexService indexService;
    private final DocumentService documentService;

    @Autowired
    public ProductService(IndexService indexService, DocumentService documentService) {
        this.indexService = indexService;
        this.documentService = documentService;
    }

    public void createIndex() {
        try {
            File file = ResourceUtils.getFile("classpath:elastic/store-product-mapping.json");
            FileReader fileReader = new FileReader(file);
            String body = FileCopyUtils.copyToString(fileReader);
            fileReader.close();
            indexService.createIndex(INDEX, body);
        } catch (IOException e) {
            logger.warn("Could not read mapping file for creating index", e);
        }
    }

    public void storeMsgFromMQ(byte[] body) {
        //HashMap<String, Object> products = new HashMap<>();
        documentService.StoreOriginal(INDEX, TYPE, body);
    }

    class CriticalConsumer extends DefaultConsumer {
        ExecutorService threadPool = null;
        public CriticalConsumer(Channel channel, final ExecutorService threadPool) {
            super(channel);
            this.threadPool = threadPool;
        }

        public void handleDelivery(String consumerTag, Envelope envelope,
                                   AMQP.BasicProperties properties, byte[] body) throws UnsupportedEncodingException {
            String message = new String(body, "UTF-8");

//            logger.info("consumertag: " + consumerTag + " Envelope : " + envelope.toString() +
//                    " BasicProperties: " + properties);
            Channel channel = this.getChannel();
            String routingKey = envelope.getRoutingKey();
            int  number = 0;
            try {
                logger.info(String.format("Received (channel %d) %s", channel.getChannelNumber(), routingKey));
                number++;
                threadPool.submit(new Runnable() {
                    public void run() {
                        try {
                            documentService.StoreOriginal(INDEX, TYPE, body);
                            channel.basicAck(envelope.getDeliveryTag(), false);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (Exception e) {
                logger.error("", e);
            }
            logger.info(" [x] Created Received '" + number + "'");
        }
    }
    class CommonConsumer extends DefaultConsumer {
        public CommonConsumer(Channel channel) {
            super(channel);
        }

        public void handleDelivery(String consumerTag, Envelope envelope,
                                   AMQP.BasicProperties properties, byte[] body) throws UnsupportedEncodingException {
            String message = new String(body, "UTF-8");

//            logger.info("consumertag: " + consumerTag + " Envelope : " + envelope.toString() +
//                    " BasicProperties: " + properties);
            Channel channel = this.getChannel();
            String rouingKey = envelope.getRoutingKey();
            int  number = 0;
            try {
                logger.info(String.format("Received (channel %d) %s", channel.getChannelNumber(), rouingKey));
                number++;
                try {
                    documentService.StoreOriginal(INDEX, TYPE, body);
                    channel.basicAck(envelope.getDeliveryTag(), false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                logger.error("", e);
            }
            logger.info(" [x] Created Received '" + number + "'");
        }
    }
    public void startChanel(Channel channel, String queueName) throws  TimeoutException, IOException{
        logger.info("Start channel...");
        //Channel channel = connection.createChannel();
        //channel.queueBind(PublishMsg.ELASTICQUEUENAME, PublishMsg.EXCHANGE_NAME, "Product.CREATE.#");
        int threadNumber = 5;
        final ExecutorService threadPool =  new ThreadPoolExecutor(threadNumber, threadNumber,
                0L, TimeUnit.MILLISECONDS,

                new LinkedBlockingQueue<Runnable>());

        channel.basicConsume(queueName, false, new ProductService.CriticalConsumer(channel, threadPool));

        //channel.basicConsume(PublishMsg.ELASTICQUEUENAME, false, new ProductService.CommonConsumer(channel));
    }
    public void consumeMsg() throws TimeoutException, IOException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");//10.128.165.206
        connectionFactory.setPort(56672);
        connectionFactory.setUsername("root");
        connectionFactory.setPassword("Initial0");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(PublishMsg.EXCHANGE_NAME,"topic");
        //channel.queueBind(PublishMsg.ELASTICQUEUENAME, PublishMsg.EXCHANGE_NAME, "Product.CREATE.#");

        String queueName = channel.queueDeclare().getQueue();
        logger.info("Channel queue name: "+ queueName);
        channel.queueBind(queueName, PublishMsg.EXCHANGE_NAME, "Product.CREATE.#");
        startChanel(channel, queueName);


        Channel channel1 = connection.createChannel();
        //channel1.queueBind(PublishMsg.ELASTICQUEUENAME, PublishMsg.EXCHANGE_NAME, "Product.UPDATE.#");
        queueName = channel.queueDeclare().getQueue();
        logger.info("Channel queue name: "+ queueName);
        channel.queueBind(queueName, PublishMsg.EXCHANGE_NAME, "Product.UPDATE.#");

        startChanel(channel1, queueName);
        //startChanel(connection);
    }

}
