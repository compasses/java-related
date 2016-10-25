package jet.mq.messageconsume;

import jet.mq.messageconsume.messageProc.MessageHandler;
import org.apache.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.regex.Matcher;

/**
 * Created by I311352 on 10/14/2016.
 */

@Service
public class SimpleListener implements MessageListener {
    public static Logger logger = Logger.getLogger(SimpleListener.class);
    private HashMap<String, Integer> stats = new HashMap<>();

    private static class SingletonHelper {
        private static final SimpleListener instance = new SimpleListener();
    }

    public static SimpleListener getInstance() {
        return SingletonHelper.instance;
    }


    public void onMessage(Message msg) {
        String routingKey = msg.getMessageProperties().getReceivedRoutingKey();
        Long tenantId = Long.parseLong(msg.getMessageProperties().getHeaders().get("X-Tenant-ID").toString());
        logger.info("routing Key -> " + routingKey);

        Matcher m = MQ_Constants.ROUTINGKEY_PATTERN.matcher(routingKey);
        if (m.find()) {
            String boType = m.group(1);
            String action = m.group(2);
            MessageHandler messageHandler = MQ_Constants.routingMsg.get(boType+".#");
            if (messageHandler != null) {
                messageHandler.dowork(boType, action, tenantId, msg.getBody());

                if (stats.containsKey(routingKey)) {
                    Integer v = stats.get(routingKey);
                    stats.put(routingKey, ++v);
                } else {
                    stats.put(routingKey, 1);
                }
            }
            return;
        }
        logger.warn("Cannot find type and action or handler: " + routingKey);
    }

    public HashMap<String, Integer> getStats() {
        return stats;
    }

    public void setStats(HashMap<String, Integer> stats) {
        this.stats = stats;
    }
}
