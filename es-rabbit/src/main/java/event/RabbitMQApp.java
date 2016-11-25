package event;

/**
 * Created by I311352 on 11/23/2016.
 */
import event.publish.MessagePublishService;
import org.apache.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class RabbitMQApp {
    private static final Logger logger = Logger.getLogger(RabbitMQApp.class);

    public static void main(String[] args) {
        SpringApplication.run(RabbitMQApp.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            logger.info("Start publish message...");
            MessagePublishService publishService = ctx.getBean(MessagePublishService.class);
            if (System.getProperty("rabbit.cfg") != null) {
                logger.info("Use user config rabbit: " + System.getProperty("rabbit.cfg"));
                publishService.setRabbitProperties(System.getProperty("rabbit.cfg"));
            }
            if (System.getProperty("message.src") != null) {
                logger.info("Use user message source: " + System.getProperty("message.src"));
                publishService.setMessageSource(System.getProperty("message.src"));
            }
            publishService.publish();
        };
    }
}
