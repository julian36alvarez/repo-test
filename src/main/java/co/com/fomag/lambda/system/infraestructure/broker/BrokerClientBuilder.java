package co.com.fomag.lambda.system.infraestructure.broker;

import software.amazon.awssdk.services.sqs.SqsClient;

public interface BrokerClientBuilder {

	SqsClient getClient(String region);
}
