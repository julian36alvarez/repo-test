package co.com.fomag.lambda.content.application.ports.in.execute;

import co.com.fomag.lambda.content.application.domain.ProxyRequest;
import co.com.fomag.lambda.content.application.domain.ProxyResponse;

public interface ExecuteRequestUseCase {

	ProxyResponse execute(ProxyRequest request);
}