package ie.gmit.sw;

import java.io.IOException;

import org.apache.commons.lang.SerializationUtils;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class InQueueService {
	// private static lazy singleton instance
	private static InQueueService instance;

	private final static String HOST = "localhost";
	private final static String QUEUE_NAME = "INQUEUE";
	private Connection connection;
	private Channel channel;
	private Consumer consumer;
	
	/*
	 * Get a handle on the RabbitMQ Connection Factory
	 * Create a new Connection
	 * Create a new Channel
	 */
	private InQueueService() throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(HOST);
		this.connection = factory.newConnection();
		this.channel = connection.createChannel();
		this.channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		
		this.consumer = new InnerRequestHandler(this.channel);
	}
	
	/*
	 * Static Singleton Factory method to get a handle on the instance
	 */
	public static InQueueService getInstance() throws Exception {
		if(instance == null) {
			instance = new InQueueService();
		}
		return instance;
	}
	
	/*
	 * Serialize the Request to the RabbitMQ Message Server - INQUEUE
	 * via channel.basicPublish() using SerializationUtils
	 */
	public void queueRequest(Request req) throws IOException {
		channel.basicPublish("", QUEUE_NAME, null, SerializationUtils.serialize(req));
	}
	
	/*
	 * Consume the Request from the Queue
	 * via channel.basicConsume() passing the Consumer defined below as - InnerRequestHandler
	 */
	public void consumeRequest() throws IOException {
		channel.basicConsume(QUEUE_NAME, true, consumer);
	}
	
	@Override
	protected void finalize() throws Throwable {
		// Close connections when the object is out of scope and garbage collected
		this.connection.close();
		this.channel.close();
	}
	
	/*
	 * Inner class - InnerRequestHandler is responsible for consuming requests from the INQUEUE
	 * And dispatching the requests to RMIClient worker threads
	 */
	private class InnerRequestHandler extends DefaultConsumer {
		
		private InnerRequestHandler(Channel channel) {
			super(channel);
		}
		
		@Override
		public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
				throws IOException {
			Request req = (Request) SerializationUtils.deserialize(body);
			
			new Thread(new RMIClient(req)).start();
		}
	}
}
