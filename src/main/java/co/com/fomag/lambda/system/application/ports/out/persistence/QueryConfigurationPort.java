package co.com.fomag.lambda.system.application.ports.out.persistence;

import co.com.fomag.lambda.system.application.domain.ConfigRequest;

public interface QueryConfigurationPort {

	ConfigRequest query(String key, String sortKey);
}
