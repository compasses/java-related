package jet.mq.messageconsume;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * Created by I311352 on 10/13/2016.
 */
@Service
public class Consumers {
//    static {
//        ConnectionFactory cf = new CachingConnectionFactory("10.128.165.206", 5672);
//        RabbitAdmin admin = new RabbitAdmin(cf);
//
//        Queue queue = new Queue(MQ_Constants.ELASTICQUEUENAME, false);
//        admin.declareQueue(queue);
//
//        TopicExchange exchange = new TopicExchange(MQ_Constants.EXCHANGE_NAME);
//        admin.declareExchange(exchange);
//
//        Arrays.stream(MQ_Constants.routingMsg.keySet().toArray()).forEach(
//                name ->{
//                    admin.declareBinding(
//                            BindingBuilder.bind(queue).to(exchange).with((String) name));
//                }
//        );
//
//        // set up the listener and container
//        SimpleMessageListenerContainer container =
//                new SimpleMessageListenerContainer(cf);
//
//        MessageListenerAdapter adapter = new MessageListenerAdapter(SimpleListener.getInstance());
//
//        container.setMessageListener(adapter);
//        container.setQueueNames(MQ_Constants.ELASTICQUEUENAME);
//        container.setConcurrentConsumers(10);
//        container.start();
//    }


}
