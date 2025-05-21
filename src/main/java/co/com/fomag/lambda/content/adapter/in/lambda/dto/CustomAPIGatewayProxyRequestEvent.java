package co.com.fomag.lambda.content.adapter.in.lambda.dto;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import io.micronaut.core.annotation.Introspected;

/**
 * Clase personalizada que extiende APIGatewayProxyRequestEvent
 * y está anotada con @Introspected para permitir introspección en Micronaut.
 */
@Introspected
public class CustomAPIGatewayProxyRequestEvent extends APIGatewayProxyRequestEvent {
    // Puedes agregar métodos o propiedades adicionales si es necesario
}