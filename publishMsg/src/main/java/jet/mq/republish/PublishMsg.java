package jet.mq.republish;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.rabbitmq.client.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.*;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * Created by I311352 on 9/30/2016.
 */
@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")
@EnableAutoConfiguration
public class PublishMsg {
    private static final Logger logger = Logger.getLogger(PublishMsg.class);
    public static final String EXCHANGE_NAME = "SharedExchange";
    private DataSource dataSource;
    private Channel channel;
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public PublishMsg() throws java.io.IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("10.128.165.206");
        connectionFactory.setPort(5672);
//        connectionFactory.setUsername("root");
//        connectionFactory.setPassword("Initial0");
        com.rabbitmq.client.Connection connection = connectionFactory.newConnection();
        this.channel = connection.createChannel();
        this.channel.exchangeDeclare(EXCHANGE_NAME, "topic", true);
    }

    public static void main( String[] args ) throws java.io.IOException, TimeoutException {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-data.xml");

        PublishMsg publishMsg = new PublishMsg();
        publishMsg.setDataSource((DataSource) context.getBean("dataSource"));
        publishMsg.publish();
    }

    public  void publish() {
        Connection conn = null;

        String sql = "SELECT MESSAGEPROPERTIES, EXCHANGENAME, ROUTINGKEY, MESSAGEBODYBYTES FROM JOBEXECRECORD";
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String properties = rs.getNString(1);
                String exchangeName = rs.getNString(2);
                String routingKey = rs.getNString(3);
                Blob msg = rs.getBlob(4);

                JsonObject object = new JsonParser().parse(properties).getAsJsonObject();
                JsonObject header = object.get("headers").getAsJsonObject();

//                Gson gson = new Gson();
//                Type stringObjectMap = new TypeToken<Map<String, String>>(){}.getType();
//                HashMap<String, String> headers = gson.fromJson(header, stringObjectMap);
                HashMap<String, Object> headers = new HashMap<>();
                headers.put("X-Tenant-ID", header.get("X-Tenant-ID").getAsLong());
                headers.put("X-Message-ID", header.get("X-Message-ID").getAsString());
                headers.put("X-User-ID", header.get("X-User-ID").getAsLong());
                headers.put("X-Employee-ID", -1L);
//                headers.put("X-Session-ID", header.get("X-Session-ID").getAsString());

                launchMsg(headers, exchangeName, routingKey, msg.getBytes(1, (int) msg.length()));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {}
            }
        }
    }

    public void launchMsg(Map<String, Object> headers, String exchangeName, String routingKey, byte[] msg) {
        if (routingKey.length() > 0) {

            logger.info("RoutingKey: " + routingKey + " msg:" + msg.toString());

            try {
                this.channel.basicPublish(exchangeName, routingKey,
                        new AMQP.BasicProperties.Builder()
                                .contentType("application/json")
                                .headers(headers)
                                .build(),
                        msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        logger.info("RoutingKey: " + routingKey + " msg:" + msg.toString());

        try {
            this.channel.basicPublish(exchangeName, routingKey,
                    new AMQP.BasicProperties.Builder()
                            .contentType("application/json")
                            .headers(headers)
                            .build(),
                    msg);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
