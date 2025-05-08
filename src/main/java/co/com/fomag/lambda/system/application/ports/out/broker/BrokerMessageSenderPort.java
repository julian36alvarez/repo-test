package co.com.fomag.lambda.system.application.ports.out.broker;

public interface BrokerMessageSenderPort {

	void sendMessage(String message, String queueUrl);
}
