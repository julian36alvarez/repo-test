package co.com.fomag.lambda.content.application.ports.out.persistence;

import co.com.fomag.lambda.content.application.domain.ConfigRequest;

public interface QueryConfigurationPort {

	ConfigRequest query(String key, String sortKey);
}
