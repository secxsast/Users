
import javax.servlet.http.*;
import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

public class Send {

    @Value("${app.awsServices.rabbitMQName1}")
    private String QUEUE_NAME_1;

    @Value("${app.awsServices.rabbitMQName2}")
    private String QUEUE_NAME_2;

    public void publish_1(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME_1, false, false, false, null);
            String message = "Hello World!";
            channel.basicPublish("", QUEUE_NAME_1, null, message.getBytes(StandardCharsets.UTF_8));
            out.println(" [x] Sent '" + message + "'");
        }
    }
    public void publish_2(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME_2, false, false, false, null);
            String message = "Hello Miki!";
            channel.basicPublish("", QUEUE_NAME_2, null, message.getBytes(StandardCharsets.UTF_8));
            out.println(" [x] Sent '" + message + "'");
        }
    }
}
