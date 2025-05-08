package co.com.fomag.lambda.system.adapter.out.secretmanager;

import co.com.fomag.lambda.system.application.ports.out.secretmanager.SecretManagerPort;
import co.com.fomag.lambda.system.infraestructure.annotations.Adapter;
import co.com.fomag.lambda.system.infraestructure.parameterstore.SecretManagerBuilder;

import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParameterRequest;

@Adapter
public class SecretManagerAdapter implements SecretManagerPort {

	private final SsmClient client;

	public SecretManagerAdapter(SecretManagerBuilder builder) {
		this.client = builder.buildClient();
	}

	@Override
	public String getParameter(String name) {
		var parameterRequest = GetParameterRequest.builder().name(name).withDecryption(true).build();
		var parameterResult = client.getParameter(parameterRequest);
		return parameterResult.parameter().value();
	}

	@Override
	public String getParameter(String name, boolean decrypt) {
		var parameterRequest = GetParameterRequest.builder().name(name).withDecryption(decrypt).build();
		var parameterResult = client.getParameter(parameterRequest);
		return parameterResult.parameter().value();
	}

}
