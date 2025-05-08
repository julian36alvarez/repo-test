package co.com.fomag.lambda.system.infraestructure.parameterstore;

import software.amazon.awssdk.services.ssm.SsmClient;

public interface SecretManagerBuilder {

	SsmClient buildClient();
}
