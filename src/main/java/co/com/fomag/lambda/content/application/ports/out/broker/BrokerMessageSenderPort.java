package co.com.fomag.lambda.content.application.ports.out.broker;

public interface BrokerMessageSenderPort {

	void sendMessage(String message, String queueUrl);
}
