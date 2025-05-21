package co.com.fomag.lambda.content.infraestructure.parameterstore;

import software.amazon.awssdk.services.ssm.SsmClient;

public interface SecretManagerBuilder {

	SsmClient buildClient();
}
