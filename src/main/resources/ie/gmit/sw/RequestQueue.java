package ie.gmit.sw;

import java.io.IOException;

import org.apache.commons.lang.SerializationUtils;

import com.rabbitmq.client.*;

public class RequestQueue {
	private final static String HOST = "localhost";
	private final static String QUEUE_NAME = "INQUEUE";
	private Channel channel;
	
	public RequestQueue() throws IOException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(HOST);
		Connection connection = factory.newConnection();
		this.channel = connection.createChannel();
		this.channel.queueDeclare(QUEUE_NAME, false, false, false, null);
	}
	
	public void queueRequest(Request req) throws IOException {
		channel.basicPublish("", QUEUE_NAME, null, SerializationUtils.serialize(req));
	}
}
