package ie.gmit.sw;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.SerializationUtils;

import com.rabbitmq.client.*;

public class RequestHandler extends DefaultConsumer {
	private ExecutorService executorService;

	public RequestHandler(Channel channel) throws Exception {
		super(channel);
        //channel.basicConsume("INQUEUE", false, this);
		executorService = Executors.newFixedThreadPool(3);
	}

	@Override
	public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
			throws IOException {
		//Runnable task = new VariableLengthTask(this, envelope.getDeliveryTag(), channel);
		Request req = (Request) SerializationUtils.deserialize(body);
		executorService.submit(new RMIClient(req));
	}
}
