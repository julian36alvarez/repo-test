package co.com.fomag.lambda.content.adapter.out.persistence.dynamodb;

import java.util.ArrayList;
import java.util.List;

import co.com.fomag.lambda.content.adapter.out.persistence.dynamodb.entities.AdditionalHeaderEntity;
import co.com.fomag.lambda.content.adapter.out.persistence.dynamodb.entities.ConfigurationEntity;
import co.com.fomag.lambda.content.application.domain.AuthType;
import co.com.fomag.lambda.content.application.domain.ConfigRequest;
import co.com.fomag.lambda.content.application.domain.HttpMethodType;
import co.com.fomag.lambda.content.application.domain.RequestParam;
import co.com.fomag.lambda.content.application.ports.out.environment.EnvironmentVariablesPort;
import co.com.fomag.lambda.content.application.ports.out.persistence.QueryConfigurationPort;
import co.com.fomag.lambda.content.infraestructure.annotations.Adapter;
import co.com.fomag.lambda.content.infraestructure.database.DynamoClientBuilder;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.Key;

@Adapter
public class ConfigurationEntityDynamoAdapter implements QueryConfigurationPort {

	private final DynamoDbEnhancedClient client;

	private final String tableName;

	public ConfigurationEntityDynamoAdapter(final DynamoClientBuilder clientBuilder,
			EnvironmentVariablesPort environmentVariables) {
		this.client = clientBuilder.getClient(environmentVariables.getRegion());
		this.tableName = environmentVariables.getConfigurationTable();
	}

	@Override
	public ConfigRequest query(String key, String sortKey) {

		var table = client.table(tableName, ConfigurationEntity.TABLE_SCHEMA);

		var item = table.getItem(Key.builder().partitionValue(key).sortValue(sortKey).build());

		return entityToDomain(item);
	}

	private ConfigRequest entityToDomain(ConfigurationEntity entity) {

		if (entity == null) {
			return null;
		}

		var domain = new ConfigRequest();

		domain.setId(entity.getId());
		domain.setOperation(entity.getOperation());
		domain.setAuthType(AuthType.getType(entity.getAuthType()));
		domain.setEndpoint(entity.getEndpoint());
		domain.setAuthParameterName(entity.getAuthParameterName());
		domain.setMethodType(HttpMethodType.getType(entity.getMethodType()));
		domain.setAdditionalHeaders(entityToDto(entity.getAdditionalHeaders()));
		domain.setResponseHeaders(entity.getResponseHeaders());
		domain.setPath(entity.getPath());

		return domain;
	}

	private List<RequestParam> entityToDto(List<AdditionalHeaderEntity> headersEntity) {

		List<RequestParam> headers = new ArrayList<>();

		if (headersEntity != null) {
			headersEntity.forEach(e -> {
				var domain = new RequestParam();
				domain.setName(e.getName());
				domain.setValue(e.getValue());
				headers.add(domain);
			});
		}

		return headers;
	}
}
