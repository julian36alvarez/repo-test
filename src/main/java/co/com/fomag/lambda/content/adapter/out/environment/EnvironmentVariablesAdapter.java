package co.com.fomag.lambda.content.adapter.out.environment;

import co.com.fomag.lambda.content.application.ports.out.environment.EnvironmentConstants;
import co.com.fomag.lambda.content.application.ports.out.environment.EnvironmentVariablesPort;
import co.com.fomag.lambda.content.infraestructure.annotations.Adapter;

@Adapter
public class EnvironmentVariablesAdapter implements EnvironmentVariablesPort {

	@Override
	public String getEnvironmentVariable(String key) {
		return System.getenv(key);
	}

	@Override
	public String getRegion() {
		return getEnvironmentVariable(EnvironmentConstants.REGION);
	}

	@Override
	public String getConfigurationTable() {
		return getEnvironmentVariable(EnvironmentConstants.CONFIGURATION_TABLE_NAME);
	}

	@Override
	public String getMonitorQueue() {
		return getEnvironmentVariable(EnvironmentConstants.ENV_MONITOR_QUEUE);
	}
}