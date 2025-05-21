package co.com.fomag.lambda.content.infraestructure.context;

import java.util.HashMap;
import java.util.Map;

import co.com.fomag.lambda.content.application.ports.out.secretmanager.SecretManagerPort;
import co.com.fomag.lambda.content.adapter.out.secretmanager.SecretManagerAdapter;
import co.com.fomag.lambda.content.adapter.out.broker.sqs.SqsMessageSenderAdapter;
import co.com.fomag.lambda.content.adapter.out.environment.EnvironmentVariablesAdapter;
import co.com.fomag.lambda.content.adapter.out.persistence.dynamodb.ConfigurationEntityDynamoAdapter;
import co.com.fomag.lambda.content.adapter.out.web.HttpExecutorAdapter;
import co.com.fomag.lambda.content.application.ports.in.execute.ExecuteRequestUseCase;
import co.com.fomag.lambda.content.application.ports.out.broker.BrokerMessageSenderPort;
import co.com.fomag.lambda.content.application.ports.out.environment.EnvironmentVariablesPort;
import co.com.fomag.lambda.content.application.ports.out.persistence.QueryConfigurationPort;
import co.com.fomag.lambda.content.application.ports.out.web.HttpExecutorPort;
import co.com.fomag.lambda.content.application.services.ExecuteRequestService;
import co.com.fomag.lambda.content.infraestructure.broker.BrokerClient;
import co.com.fomag.lambda.content.infraestructure.broker.BrokerClientBuilder;
import co.com.fomag.lambda.content.infraestructure.database.DynamoClient;
import co.com.fomag.lambda.content.infraestructure.database.DynamoClientBuilder;
import co.com.fomag.lambda.content.infraestructure.parameterstore.SecretManagerBuilder;
import co.com.fomag.lambda.content.infraestructure.parameterstore.SecretManagerClient;

public class AppContext {

	private static Map<String, Object> classes = new HashMap<>();

	private AppContext() {
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> clazz) {

		if (classes.containsKey(clazz.getSimpleName())) {
			return (T) classes.get(clazz.getSimpleName());
		}

		var newClazz = getInnerBean(clazz);

		if (newClazz == null) {
			return null;
		}

		classes.put(newClazz.getClass().getSimpleName(), newClazz);

		return newClazz;
	}

	@SuppressWarnings("unchecked")
	private static <T> T getInnerBean(Class<T> clazz) {

		if (clazz.isAssignableFrom(ExecuteRequestUseCase.class)) {
			return (T) new ExecuteRequestService(getInnerBean(QueryConfigurationPort.class),
					getInnerBean(HttpExecutorPort.class), getInnerBean(BrokerMessageSenderPort.class),
					getInnerBean(EnvironmentVariablesPort.class));
		}

		if (clazz.isAssignableFrom(QueryConfigurationPort.class)) {
			return (T) new ConfigurationEntityDynamoAdapter(getInnerBean(DynamoClientBuilder.class),
					getInnerBean(EnvironmentVariablesPort.class));
		}

		if (clazz.isAssignableFrom(HttpExecutorPort.class)) {
			return (T) new HttpExecutorAdapter();
		}

		if (clazz.isAssignableFrom(DynamoClientBuilder.class)) {
			return (T) new DynamoClient();
		}

		if (clazz.isAssignableFrom(EnvironmentVariablesPort.class)) {
			return (T) new EnvironmentVariablesAdapter();
		}

		if (clazz.isAssignableFrom(SecretManagerBuilder.class)) {
			return (T) new SecretManagerClient();
		}

		if (clazz.isAssignableFrom(BrokerClientBuilder.class)) {
			return (T) new BrokerClient();
		}

		if (clazz.isAssignableFrom(BrokerMessageSenderPort.class)) {
			return (T) new SqsMessageSenderAdapter(getInnerBean(BrokerClientBuilder.class),
					getInnerBean(EnvironmentVariablesPort.class));
		}

		if (clazz.isAssignableFrom(SecretManagerPort.class)) {
			return (T) new SecretManagerAdapter(getInnerBean(SecretManagerBuilder.class));
		}

		return null;
	}
}
