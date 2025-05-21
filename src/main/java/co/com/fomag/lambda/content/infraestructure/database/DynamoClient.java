package co.com.fomag.lambda.content.infraestructure.database;

import co.com.fomag.lambda.content.infraestructure.annotations.Component;

import software.amazon.awssdk.auth.credentials.ContainerCredentialsProvider;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.core.SdkSystemSetting;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Component
public class DynamoClient implements DynamoClientBuilder {

	private DynamoDbEnhancedClient enhancedClient;

	public DynamoDbEnhancedClient getClient(String region) {

		if (enhancedClient == null) {
			var dynamoDbClient = DynamoDbClient.builder().region(Region.of(region))
					.credentialsProvider(
							SdkSystemSetting.AWS_CONTAINER_CREDENTIALS_FULL_URI.getStringValue().isPresent()
									? ContainerCredentialsProvider.builder().build()
									: EnvironmentVariableCredentialsProvider.create())
					.build();
			enhancedClient = DynamoDbEnhancedClient.builder().dynamoDbClient(dynamoDbClient).build();
		}

		return enhancedClient;
	}
}
