package ie.gmit.sw;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.SerializationUtils;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

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
		
		this.consumer = new InnerRequestHandler(this.channel);
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
	
	/*
	 * Inner class - InnerRequestHandler is responsible for consuming requests from the INQUEUE
	 * And processing with the request via a new RMIClient worker thread dispatched using an ExecutorService
	 */
	private class InnerRequestHandler extends DefaultConsumer {
		private ExecutorService executorService;
		
		public InnerRequestHandler(Channel channel) {
			super(channel);
			this.executorService = Executors.newFixedThreadPool(5);
		}
		
		@Override
		public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
				throws IOException {
			Request req = (Request) SerializationUtils.deserialize(body);
			executorService.submit(new RMIClient(req));
		}
	}
}
