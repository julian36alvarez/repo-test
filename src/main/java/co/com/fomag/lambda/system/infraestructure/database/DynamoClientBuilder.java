package co.com.fomag.lambda.system.infraestructure.database;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;

public interface DynamoClientBuilder {

	DynamoDbEnhancedClient getClient(String region);
}
