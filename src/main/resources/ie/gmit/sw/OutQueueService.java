package ie.gmit.sw;

import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.lang.SerializationUtils;

import com.rabbitmq.client.*;

public class OutQueueService {
	private HashMap<Long, Response> responseMap;
	private final static String HOST = "localhost";
	private final static String QUEUE_NAME = "OUTQUEUE";
	private Channel channel;
	private Consumer consumer;
	
	/*
	 * Get a handle on the RabbitMQ Connection Factory
	 * Create a new Connection
	 * Create a new Channel
	 */
	public OutQueueService() throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(HOST);
		Connection connection = factory.newConnection();
		this.channel = connection.createChannel();
		this.channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		
		this.consumer = new ResponseHandler(this.channel);
		this.responseMap = new HashMap<Long, Response>();
	}
	
	/*
	 * Serialize the Response to the RabbitMQ Message Server
	 */
	public void queueResponse(Response resp) throws IOException {
		channel.basicPublish("", QUEUE_NAME, null, SerializationUtils.serialize(resp));
	}
	
	public void consumeResponse() throws IOException {
		channel.basicConsume(QUEUE_NAME, true, consumer);
	}
	
	public Response pollResponse(long taskId) {
		System.out.println("Printing from pollResponse: " + responseMap);
		Response resp = responseMap.get(taskId);
		//responseMap.remove(taskId);
		return resp;
	}
	
	private class ResponseHandler extends DefaultConsumer {
			
		public ResponseHandler(Channel channel) {
			super(channel);
		}
		
		@Override
		public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
				throws IOException {
			// Handle the Response
			Response resp = (Response) SerializationUtils.deserialize(body);
			// Log the Response
			System.out.println("Logging Response - @handleDelivery: " + resp);
			// Store in the HashMap
			System.out.println("Storing in map");
			responseMap.put(resp.getTaskId(), resp);
			System.out.println(responseMap);
		}
	}
}
