package jet.mq.republish;

import com.rabbitmq.client.*;
import lession.app.context.rabbit.tutorial.alertRecv;
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
import java.sql.*;
import java.sql.Connection;
import java.util.concurrent.TimeoutException;

/**
 * Created by I311352 on 9/30/2016.
 */
@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")
@EnableAutoConfiguration
public class PublishMsg {
    private static final Logger logger = Logger.getLogger(alertRecv.class);
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

        String sql = "SELECT EXCHANGENAME, ROUTINGKEY, MESSAGEBODYBYTES FROM JOBEXECRECORD";
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String exchangeName = rs.getNString(1);
                String routingKey = rs.getNString(2);
                Blob msg = rs.getBlob(3);
                logger.info("RoutingKey: " + routingKey + " msg:" + msg.toString());
                launchMsg(exchangeName, routingKey, msg.getBytes(1, (int) msg.length()));
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

    public void launchMsg(String exchangeName, String routingKey, byte[] msg) {
        try {
            this.channel.basicPublish(exchangeName, routingKey,
                    new AMQP.BasicProperties.Builder()
                            .contentType("application/json")
//                    .deliveryMode(2)
//                    .priority(1)
//                    .userId("bob")
                            .build(),
                    msg);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
//    public void originalPublish() {
//        Connection con = null;
//        Statement st = null;
//        ResultSet rs = null;
//
//        String url = "jdbc:mysql://10.128.161.143:3306/channeladapter";
//        String user = "root";
//        String password = "Initial0";
//
//        try {
//            con = DriverManager.getConnection(url, user, password);
//            st = con.createStatement();
//            rs = st.executeQuery("SELECT VERSION()");
//            if (rs.next()) {
//                System.out.println(rs.getString(1));
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        } finally {
//            try {
//                if (rs != null) {
//                    rs.close();
//                }
//                if (st != null) {
//                    st.close();
//                }
//                if (con != null) {
//                    con.close();
//                }
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//        }
//    }
}