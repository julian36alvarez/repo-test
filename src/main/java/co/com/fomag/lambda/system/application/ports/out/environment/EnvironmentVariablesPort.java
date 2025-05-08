package co.com.fomag.lambda.system.application.ports.out.environment;

public interface EnvironmentVariablesPort {

	String getEnvironmentVariable(String key);

	String getRegion();

	String getConfigurationTable();

	String getMonitorQueue();
}
