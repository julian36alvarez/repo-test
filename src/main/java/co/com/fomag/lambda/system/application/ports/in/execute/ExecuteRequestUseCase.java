package co.com.fomag.lambda.system.application.ports.in.execute;

import co.com.fomag.lambda.system.application.domain.ProxyRequest;
import co.com.fomag.lambda.system.application.domain.ProxyResponse;

public interface ExecuteRequestUseCase {

	ProxyResponse execute(ProxyRequest request);
}