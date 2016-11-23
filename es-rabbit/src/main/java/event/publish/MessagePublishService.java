package event.publish;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.sap.sme.vault.infra.ConfigurationProvider;
import com.sap.sme.vault.infra.impl.ConfigurationProviderSingleton;
import com.sap.sme.vault.infra.model.NameAndPwd;
import event.RabbitMQApp;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * Created by I311352 on 11/23/2016.
 */

@Service
public class MessagePublishService {
    private static final Logger logger = Logger.getLogger(MessagePublishService.class);
    public static final String EXCHANGE_NAME = "SharedExchange";
    private Channel channel;

    public MessagePublishService() {
    }

    public void publish() {
        if (!createChannel()) {
            logger.error("Channel Create Failed, Do Nothing");
            return;
        }
        logger.info("Creaet RabbitMQ Channel Success, Start to publish message");

    }

    private boolean createChannel() {
        try {
            logger.info("Get rabbitMQ Channel START!");
            String PRODUCTION_RABBITMQ_PROPERTIES_LOCATION = "/etc/secrets/rabbitmq/rabbitmq.properties";
            File rabbitFile = new File(PRODUCTION_RABBITMQ_PROPERTIES_LOCATION);
            InputStream inputStream = null;
            Properties props = new Properties();
            String rabbitusr = null;
            String rabbitpwd = null;
            if (!rabbitFile.exists()) {
                logger.info("Cannot find rabbitmq.properties in the production environment. Using local file!");
                URL resource = RabbitMQApp.class.getClassLoader().getResource("rabbitmq.properties");
                inputStream = new FileInputStream(resource.getFile());
                props.load(inputStream);
                rabbitusr = props.get("MQ_DB_USR").toString();
                rabbitpwd = props.get("MQ_DB_PWD").toString();
                inputStream.close();
            } else {
                logger.info("Load the content of the rabbitmq.properties in the production environment.");
                inputStream = new FileInputStream(rabbitFile);
                props.load(inputStream);
                ConfigurationProvider provider = ConfigurationProviderSingleton.getInstance();
                NameAndPwd nameAndPwd = provider.getRabbitMQCreds();
                rabbitusr = nameAndPwd.getUsername();
                rabbitpwd = nameAndPwd.getPassword();
                inputStream.close();
            }
            String rabbithost = props.get("MQ_HOST").toString();
            String rabbitport = props.get("MQ_PORT").toString();

            ConnectionFactory rabbitFactory = new ConnectionFactory();
            rabbitFactory.setHost(rabbithost);
            rabbitFactory.setUsername(rabbitusr);
            rabbitFactory.setPassword(rabbitpwd);
            Long port = Long.valueOf(rabbitport);
            rabbitFactory.setPort(port.intValue());
            Connection connection = rabbitFactory.newConnection();
            this.channel = connection.createChannel();
            this.channel.exchangeDeclare(EXCHANGE_NAME, "topic", true);
            return true;
        } catch (Exception e) {
            logger.error("Exception during getChannel " + e);
        }

        return false;
    }
}
