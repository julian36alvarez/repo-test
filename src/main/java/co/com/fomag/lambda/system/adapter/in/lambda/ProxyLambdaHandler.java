package co.com.fomag.lambda.system.adapter.in.lambda;

import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import co.com.fomag.lambda.system.adapter.in.lambda.dto.RequestDto;
import co.com.fomag.lambda.system.adapter.in.lambda.dto.ResponseDto;
import co.com.fomag.lambda.system.application.domain.ProxyRequest;
import co.com.fomag.lambda.system.application.ports.in.execute.ExecuteRequestUseCase;
import co.com.fomag.lambda.system.application.services.StaticBuilder;
import co.com.fomag.lambda.system.infraestructure.annotations.Component;
import co.com.fomag.lambda.system.infraestructure.context.AppContext;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.util.StringUtils;
import io.micronaut.function.executor.FunctionInitializer;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Introspected
@Component
public class ProxyLambdaHandler extends FunctionInitializer
		implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

	private static final String CONTENT_TYPE = "Content-Type";

	private static final APIGatewayProxyResponseEvent proxyResponse = new APIGatewayProxyResponseEvent();

	private ExecuteRequestUseCase executeRequestUseCase = AppContext.getBean(ExecuteRequestUseCase.class);

	@Override
	public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {

		proxyResponse.setHeaders(Map.of(CONTENT_TYPE, MediaType.APPLICATION_JSON));

		try {

			log.info("Start lambda handler ...");

			var request = isValidInput(input);

			log.debug("Request {}", request);

			var response = new ResponseDto();

			if (request == null) {
				response.setHttpCode(400);
				response.setMessage(HttpStatus.BAD_REQUEST.name());
				response.setMessageCode(HttpStatus.BAD_REQUEST.getReason());
				proxyResponse.setStatusCode(response.getHttpCode());
				proxyResponse.setBody(StaticBuilder.mapper.writeValueAsString(response));

				return proxyResponse;
			}

			var resp = executeRequestUseCase.execute(inputToDomain(request));

			var responseContent = resp.getBody();

			response.setContent(responseContent == null ? null : StaticBuilder.mapper.readTree(resp.getBody()));
			response.setResponseHeaders(resp.getHeaders());
			response.setHttpCode(200);
			response.setMessage(HttpStatus.OK.name());
			response.setMessageCode(HttpStatus.OK.getReason());

			proxyResponse.setBody(StaticBuilder.mapper.writeValueAsString(response));
			proxyResponse.setStatusCode(response.getHttpCode());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			var response = new ResponseDto();
			response.setHttpCode(500);
			response.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.name());
			response.setMessageCode(HttpStatus.INTERNAL_SERVER_ERROR.getReason());
			proxyResponse.setStatusCode(response.getHttpCode());
			try {
				proxyResponse.setBody(StaticBuilder.mapper.writeValueAsString(response));
			} catch (JsonProcessingException e1) {
				log.error(e.getMessage(), e1);
			}
		}

		return proxyResponse;
	}

	private RequestDto isValidInput(APIGatewayProxyRequestEvent input) {
		var body = input.getBody();

		if (!StringUtils.hasText(body)) {
			return null;
		}

		try {
			return StaticBuilder.mapper.readValue(body, RequestDto.class);
		} catch (JsonProcessingException e) {
			log.error(e.getMessage(), e);
		}

		return null;
	}

	private ProxyRequest inputToDomain(RequestDto input) {
		var proxy = new ProxyRequest();
		proxy.setId(input.getId());
		proxy.setOperation(input.getOperation());
		proxy.setHeaders(input.getHeaders());
		proxy.setPathParams(input.getPathParams());
		proxy.setQueryParams(input.getQueryParams());
		proxy.setBody(input.getBody());

		return proxy;
	}
}
