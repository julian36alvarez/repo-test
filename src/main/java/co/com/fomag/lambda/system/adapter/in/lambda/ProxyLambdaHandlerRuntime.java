package co.com.fomag.lambda.system.adapter.in.lambda;

import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import io.micronaut.function.aws.runtime.AbstractMicronautLambdaRuntime;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProxyLambdaHandlerRuntime extends
		AbstractMicronautLambdaRuntime<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent, APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

	public static void main(String[] args) throws Exception {
		log.info("Start ProxyLambdaHandlerRuntime ...");
		new ProxyLambdaHandlerRuntime().run(args);
	}

	@Override
	protected RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> createRequestHandler(
			String... args) {
		return new ProxyLambdaHandler();
	}
}
