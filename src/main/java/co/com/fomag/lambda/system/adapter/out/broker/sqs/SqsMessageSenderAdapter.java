package co.com.fomag.lambda.system.adapter.out.broker.sqs;

import co.com.fomag.lambda.system.application.ports.out.broker.BrokerMessageSenderPort;
import co.com.fomag.lambda.system.application.ports.out.environment.EnvironmentVariablesPort;
import co.com.fomag.lambda.system.infraestructure.annotations.Adapter;
import co.com.fomag.lambda.system.infraestructure.broker.BrokerClientBuilder;

import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Adapter
@Slf4j
public class SqsMessageSenderAdapter implements BrokerMessageSenderPort {

	private final BrokerClientBuilder brokerClientBuilder;

	private final String region;

	public SqsMessageSenderAdapter(BrokerClientBuilder brokerClientBuilder,
			EnvironmentVariablesPort environmentVariables) {
		this.brokerClientBuilder = brokerClientBuilder;
		this.region = environmentVariables.getRegion();

	}

	@Override
	public void sendMessage(String message, String queueUrl) {
		log.info("Sending message to broker ... {}", queueUrl);
		var request = SendMessageRequest.builder().queueUrl(queueUrl).messageBody(message).build();
		brokerClientBuilder.getClient(this.region).sendMessage(request);
		log.info("Message sent to broker ...");
	}
}
