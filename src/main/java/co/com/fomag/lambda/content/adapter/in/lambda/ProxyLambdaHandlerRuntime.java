package co.com.fomag.lambda.content.adapter.in.lambda;

import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import io.micronaut.context.ApplicationContext;
import io.micronaut.function.aws.runtime.AbstractMicronautLambdaRuntime;
import lombok.extern.slf4j.Slf4j;

/**
 * Runtime principal para ejecutar la función Lambda usando Micronaut.
 * Este runtime es necesario cuando se requiere control manual del contexto.
 */
@Slf4j
public class ProxyLambdaHandlerRuntime extends AbstractMicronautLambdaRuntime<
		APIGatewayProxyRequestEvent,
		APIGatewayProxyResponseEvent,
		APIGatewayProxyRequestEvent,
		APIGatewayProxyResponseEvent> {

	public static void main(String[] args) throws Exception {
		log.info("Starting ProxyLambdaHandlerRuntime...");
		new ProxyLambdaHandlerRuntime().run(args);
	}

	@Override
	protected RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> createRequestHandler(String... args) {
		ApplicationContext context = ApplicationContext.builder().build().start();
		return context.getBean(ProxyLambdaHandler.class);
	}
}
