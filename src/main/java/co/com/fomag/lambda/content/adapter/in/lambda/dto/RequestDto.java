package co.com.fomag.lambda.content.adapter.in.lambda.dto;

import java.util.List;

import co.com.fomag.lambda.content.application.domain.RequestParam;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.ReflectiveAccess;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@Getter
@Setter
@Introspected
@ReflectiveAccess
@ToString
public class RequestDto {

	private String id;

	private String operation;

	private String body;

	private List<RequestParam> pathParams;

	private List<RequestParam> queryParams;

	private List<RequestParam> headers;
}
