package co.com.fomag.lambda.content.adapter.in.lambda;

import co.com.fomag.lambda.content.adapter.in.lambda.dto.ResponseDto;
import co.com.fomag.lambda.content.application.ports.in.execute.TipoDocumentoUseCase;
import co.com.fomag.lambda.content.application.services.StaticBuilder;

import co.com.fomag.lambda.content.infraestructure.annotations.Component;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.micronaut.core.annotation.Introspected;

import io.micronaut.function.aws.MicronautRequestHandler;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@Introspected
@Singleton
@Component
public class ProxyLambdaHandler extends MicronautRequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

	private static final String CONTENT_TYPE = "Content-Type";

	@Inject
	private TipoDocumentoUseCase tipoDocumentoUseCase;



	@Override
	public APIGatewayProxyResponseEvent execute(APIGatewayProxyRequestEvent input) {
		APIGatewayProxyResponseEvent proxyResponse = new APIGatewayProxyResponseEvent();
		proxyResponse.setHeaders(Map.of(CONTENT_TYPE, MediaType.APPLICATION_JSON));
		log.debug("Ejecutando lambda-------------------------------->");
		try {
			String path = input.getPath();
			log.info("Request path: {}", path);

			if ("/health".equalsIgnoreCase(path)) {
				return getAllTiposDocumento();
			}

		} catch (Exception e) {
			log.error("Error processing request", e);
			var response = new ResponseDto();
			response.setHttpCode(500);
			response.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.name());
			response.setMessageCode(HttpStatus.INTERNAL_SERVER_ERROR.getReason());
			proxyResponse.setStatusCode(response.getHttpCode());
			try {
				proxyResponse.setBody(StaticBuilder.mapper.writeValueAsString(response));
			} catch (JsonProcessingException e1) {
				log.error("Error serializing error response", e1);
			}
		}

		return proxyResponse;
	}

	private APIGatewayProxyResponseEvent getAllTiposDocumento() throws JsonProcessingException {
		APIGatewayProxyResponseEvent proxyResponse = new APIGatewayProxyResponseEvent();
		proxyResponse.setHeaders(Map.of(CONTENT_TYPE, MediaType.APPLICATION_JSON));

		log.info("Fetching all tipos de documento...");

		var tiposDocumento = tipoDocumentoUseCase.listarTodos();
		var jsonResponse = StaticBuilder.mapper.writeValueAsString(Map.of(
				"data", tiposDocumento,
				"region", System.getenv("AWS_REGION")
		));

		proxyResponse.setStatusCode(HttpStatus.OK.getCode());
		proxyResponse.setBody(jsonResponse);

		return proxyResponse;
	}



}
