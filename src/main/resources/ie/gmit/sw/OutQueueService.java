package ie.gmit.sw;

import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.lang.SerializationUtils;

import com.rabbitmq.client.*;

public class OutQueueService {
	// private static lazy singleton instance
	private static OutQueueService instance;
	
	private final static String HOST = "localhost";
	private final static String QUEUE_NAME = "OUTQUEUE";
	private Channel channel;
	private Consumer consumer;
	private HashMap<Long, Response> responseMap;

	/*
	 * Get a handle on the RabbitMQ Connection Factory
	 * Create a new Connection
	 * Create a new Channel
	 */
	private OutQueueService() throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(HOST);
		Connection connection = factory.newConnection();
		this.channel = connection.createChannel();
		this.channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		
		this.consumer = new InnerResponseHandler(this.channel);
		this.responseMap = new HashMap<Long, Response>();
	}
	
	/*
	 * Static Singleton Factory method to get a handle on the instance
	 */
	public static OutQueueService getInstance() throws Exception {
		if(instance == null) {
			instance = new OutQueueService();
		}
		return instance;
	}
	
	/*
	 * Serialize the Response to the RabbitMQ Message Server - OUTQUEUE
	 * via channel.basicPublish() using SerializationUtils
	 */
	public void queueResponse(Response resp) throws IOException {
		channel.basicPublish("", QUEUE_NAME, null, SerializationUtils.serialize(resp));
	}
	
	/*
	 * Consume the Response from the Queue
	 * via channel.basicConsume() passing the Consumer defined below as - InnerResponseHandler
	 */
	public void consumeResponse() throws IOException {
		channel.basicConsume(QUEUE_NAME, true, consumer);
	}
	
	public Response pollResponse(long taskId) {
		// Retrieve the response obj from the HashMap
		Response resp = responseMap.get(taskId);
		// Remove the response obj and return
		responseMap.remove(taskId);
		return resp;
	}
	
	private class InnerResponseHandler extends DefaultConsumer {
			
		private InnerResponseHandler(Channel channel) {
			super(channel);
		}
		
		@Override
		public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
				throws IOException {
			// Deserialize the body to a Response Object
			Response resp = (Response) SerializationUtils.deserialize(body);
			// Log the Response
			System.out.println("Logging Response - @handleDelivery: " + resp);
			// Store in the HashMap
			responseMap.put(resp.getTaskId(), resp);
		}
	}
}
