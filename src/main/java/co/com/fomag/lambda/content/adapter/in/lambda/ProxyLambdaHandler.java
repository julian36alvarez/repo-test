package co.com.fomag.lambda.content.adapter.in.lambda;

import co.com.fomag.lambda.content.adapter.in.lambda.dto.ResponseDto;
import co.com.fomag.lambda.content.adapter.in.lambda.dto.TipoDocumentoDto;
import co.com.fomag.lambda.content.application.ports.in.execute.TipoDocumentoUseCase;
import co.com.fomag.lambda.content.application.services.StaticBuilder;
import co.com.fomag.lambda.content.infraestructure.annotations.Component;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.function.executor.FunctionInitializer;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
@Introspected
@Singleton
public class ProxyLambdaHandler extends FunctionInitializer
		implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

	private static final String CONTENT_TYPE = "Content-Type";
	private static final Map<String, String> DEFAULT_HEADERS = Map.of(CONTENT_TYPE, MediaType.APPLICATION_JSON);

	private final TipoDocumentoUseCase tipoDocumentoUseCase;

	@Inject
	public ProxyLambdaHandler(TipoDocumentoUseCase tipoDocumentoUseCase) {
		this.tipoDocumentoUseCase = tipoDocumentoUseCase;
	}

	@Override
	public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
		try {
			String path = input.getPath();
			log.info("Request path: {}", path);

			if ("/health".equalsIgnoreCase(path)) {
				return getAllTiposDocumento();
			}

		} catch (Exception e) {
			log.error("Error processing request", e);
			return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor");
		}

		return buildErrorResponse(HttpStatus.NOT_FOUND, "Ruta no encontrada");
	}

	private APIGatewayProxyResponseEvent getAllTiposDocumento() throws JsonProcessingException {
		log.info("Fetching all tipos de documento...");

		List<TipoDocumentoDto> tiposDocumento = tipoDocumentoUseCase.listarTodos();
		System.out.println("Revision tipo documentos:");
		tiposDocumento.stream().forEach(System.out::println);


		JsonNode contentNode = StaticBuilder.mapper.valueToTree(tiposDocumento);

		ResponseDto response = new ResponseDto();
		response.setHttpCode(HttpStatus.OK.getCode());
		response.setMessage("Tipos de documento obtenidos exitosamente");
		response.setContent(contentNode);


		var jsonResponse = StaticBuilder.mapper.writeValueAsString(response);

		return new APIGatewayProxyResponseEvent()
				.withHeaders(DEFAULT_HEADERS)
				.withStatusCode(HttpStatus.OK.getCode())
				.withBody(jsonResponse);
	}

	private APIGatewayProxyResponseEvent buildErrorResponse(HttpStatus status, String message) {
		var response = new ResponseDto();
		response.setHttpCode(status.getCode());
		response.setMessage(status.name());
		response.setMessageCode(status.getReason());

		try {
			return new APIGatewayProxyResponseEvent()
					.withHeaders(DEFAULT_HEADERS)
					.withStatusCode(status.getCode())
					.withBody(StaticBuilder.mapper.writeValueAsString(response));
		} catch (JsonProcessingException e) {
			log.error("Error serializing error response", e);
			return new APIGatewayProxyResponseEvent()
					.withHeaders(DEFAULT_HEADERS)
					.withStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.getCode())
					.withBody("{\"message\": \"Error serializando la respuesta\"}");
		}
	}
}
