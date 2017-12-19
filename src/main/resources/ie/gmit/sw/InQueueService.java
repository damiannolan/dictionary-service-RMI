package ie.gmit.sw;

import java.io.IOException;

import org.apache.commons.lang.SerializationUtils;

import com.rabbitmq.client.*;

public class InQueueService {

	private final static String HOST = "localhost";
	private final static String QUEUE_NAME = "INQUEUE";
	private Channel channel;
	private Consumer consumer;
	
	/*
	 * Get a handle on the RabbitMQ Connection Factory
	 * Create a new Connection
	 * Create a new Channel
	 */
	public InQueueService() throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(HOST);
		Connection connection = factory.newConnection();
		this.channel = connection.createChannel();
		this.channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		
		this.consumer = new Worker(this.channel);
	}
	
	/*
	 * Serialize the Request to the RabbitMQ Message Server
	 */
	public void queueRequest(Request req) throws IOException {
		channel.basicPublish("", QUEUE_NAME, null, SerializationUtils.serialize(req));
	}
	
	public void consumeRequest() throws IOException {
		channel.basicConsume(QUEUE_NAME, true, consumer);
	}
}
