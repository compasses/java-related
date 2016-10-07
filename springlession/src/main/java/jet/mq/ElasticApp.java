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

    public static void main(String args[]) {
        ConfigurableApplicationContext context = SpringApplication.run(ElasticApp.class, args);
        IndexService indexService = context.getBean(IndexService.class);
        ProductService productService = context.getBean(ProductService.class);
        if (!indexService.indexExist(ProductService.INDEX)) {
            productService.createIndex();
        }

        try {
            productService.consumeMsg();
        } catch (IOException e) {
            logger.error("Error on consume msg", e);
        } catch (TimeoutException e) {
            logger.error("Error on consume timeout", e);
        }

    }

}

