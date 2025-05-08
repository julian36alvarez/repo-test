package co.com.fomag.lambda.system.application.ports.out.web;

import co.com.fomag.lambda.system.application.domain.ConfigRequest;
import co.com.fomag.lambda.system.application.domain.ProxyRequest;
import co.com.fomag.lambda.system.application.domain.ProxyResponse;

public interface HttpExecutorPort {

	ProxyResponse executeRequest(ProxyRequest request, ConfigRequest config);
}
