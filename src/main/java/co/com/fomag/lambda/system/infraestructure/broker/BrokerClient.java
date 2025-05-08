package co.com.fomag.lambda.system.infraestructure.broker;

import co.com.fomag.lambda.system.infraestructure.annotations.Component;

import software.amazon.awssdk.auth.credentials.ContainerCredentialsProvider;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.core.SdkSystemSetting;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

@Component
public class BrokerClient implements BrokerClientBuilder {

	private SqsClient client;

	@Override
	public SqsClient getClient(String region) {
		if (client == null) {
			client = SqsClient.builder().region(Region.of(region))
					.credentialsProvider(
							SdkSystemSetting.AWS_CONTAINER_CREDENTIALS_FULL_URI.getStringValue().isPresent()
									? ContainerCredentialsProvider.builder().build()
									: EnvironmentVariableCredentialsProvider.create())
					.build();
		}

		return client;
	}
}
