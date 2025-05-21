package co.com.fomag.lambda.content.application.ports.out.web;

import co.com.fomag.lambda.content.application.domain.ConfigRequest;
import co.com.fomag.lambda.content.application.domain.ProxyRequest;
import co.com.fomag.lambda.content.application.domain.ProxyResponse;

public interface HttpExecutorPort {

	ProxyResponse executeRequest(ProxyRequest request, ConfigRequest config);
}
