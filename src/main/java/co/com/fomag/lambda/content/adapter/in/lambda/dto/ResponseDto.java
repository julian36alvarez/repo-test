package co.com.fomag.lambda.content.adapter.in.lambda.dto;

import java.util.List;

import co.com.fomag.lambda.content.application.domain.RequestParam;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.JsonNode;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.ReflectiveAccess;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@Getter
@Setter
@Introspected
@ReflectiveAccess
@Serdeable
public class ResponseDto {

	private int httpCode;

	private String message;

	private String messageCode;

	private JsonNode content;

	private List<RequestParam> responseHeaders;
}
