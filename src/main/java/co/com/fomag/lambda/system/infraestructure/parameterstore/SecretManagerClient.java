package co.com.fomag.lambda.system.infraestructure.parameterstore;

import co.com.fomag.lambda.system.infraestructure.annotations.Component;

import software.amazon.awssdk.services.ssm.SsmClient;

@Component
public class SecretManagerClient implements SecretManagerBuilder {

	private SsmClient client;

	public SsmClient buildClient() {

		if (client == null) {
			client = SsmClient.create();
		}

		return client;
	}
}
