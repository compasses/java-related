package event;

/**
 * Created by I311352 on 11/23/2016.
 */
import event.publish.MessagePublishService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RabbitMQApp {
    public static void main(String[] args) {
        SpringApplication.run(RabbitMQApp.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
//            System.out.println("Let's inspect the beans provided by Spring Boot:");
//
//            String[] beanNames = ctx.getBeanDefinitionNames();
//            Arrays.sort(beanNames);
//            for (String beanName : beanNames) {
//                System.out.println(beanName);
//            }
            System.out.print("Start publish message...");
            MessagePublishService publishService = ctx.getBean(MessagePublishService.class);
            publishService.publish();
        };
    }
}
