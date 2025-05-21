package co.com.fomag.lambda.content.adapter.out.persistence.dynamodb.entities;

import java.util.List;
import java.util.Objects;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.ReflectiveAccess;
import lombok.Getter;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.mapper.StaticAttributeTags;

@Setter
@Getter
@Introspected
@ReflectiveAccess
public class ConfigurationEntity {

	private static final String PROXY_ID = "proxy_id";

	private static final String PROXY_OPERATION = "operation";

	private static final String ENDPOINT_URL = "endpoint";

	private static final String PATH_URL = "path";

	private static final String AUTH_TYPE = "auth_type";

	private static final String METHOD_TYPE = "method_type";

	private static final String AUTH_PARAMETER_NAME = "auth_parameter_name";

	private static final String ADDITIONAL_HEADERS = "additional_headers";

	private static final String RESPONSE_HEADERS = "response_headers";

	private String id;

	private String operation;

	private String endpoint;

	private String path;

	private String methodType;

	private String authType;

	private String authParameterName;

	private List<AdditionalHeaderEntity> additionalHeaders;

	private List<String> responseHeaders;

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConfigurationEntity other = (ConfigurationEntity) obj;
		return Objects.equals(getId(), other.getId());
	}

	public static final TableSchema<ConfigurationEntity> TABLE_SCHEMA = TableSchema.builder(ConfigurationEntity.class)
			.newItemSupplier(ConfigurationEntity::new)
			.addAttribute(String.class,
					a -> a.name(PROXY_ID).getter(ConfigurationEntity::getId).setter(ConfigurationEntity::setId)
							.tags(StaticAttributeTags.primaryPartitionKey()))
			.addAttribute(String.class,
					a -> a.name(PROXY_OPERATION).getter(ConfigurationEntity::getOperation)
							.setter(ConfigurationEntity::setOperation).tags(StaticAttributeTags.primarySortKey()))
			.addAttribute(String.class,
					a -> a.name(ENDPOINT_URL).getter(ConfigurationEntity::getEndpoint)
							.setter(ConfigurationEntity::setEndpoint))
			.addAttribute(String.class,
					a -> a.name(PATH_URL).getter(ConfigurationEntity::getPath).setter(ConfigurationEntity::setPath))
			.addAttribute(String.class,
					a -> a.name(METHOD_TYPE).getter(ConfigurationEntity::getMethodType)
							.setter(ConfigurationEntity::setMethodType))
			.addAttribute(String.class,
					a -> a.name(AUTH_TYPE).getter(ConfigurationEntity::getAuthType)
							.setter(ConfigurationEntity::setAuthType))
			.addAttribute(String.class,
					a -> a.name(AUTH_PARAMETER_NAME).getter(ConfigurationEntity::getAuthParameterName)
							.setter(ConfigurationEntity::setAuthParameterName))
			.addAttribute(
					EnhancedType.listOf(EnhancedType.documentOf(AdditionalHeaderEntity.class,
							AdditionalHeaderEntity.TABLE_SCHEMA_HEADERS)),
					a -> a.name(ADDITIONAL_HEADERS).getter(ConfigurationEntity::getAdditionalHeaders)
							.setter(ConfigurationEntity::setAdditionalHeaders))
			.addAttribute(EnhancedType.listOf(String.class), a -> a.name(RESPONSE_HEADERS)
					.getter(ConfigurationEntity::getResponseHeaders).setter(ConfigurationEntity::setResponseHeaders))
			.build();
}
