package co.com.fomag.lambda.content.application.domain;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ProxyRequest {

	private String id;

	private String operation;

	private String body;

	private List<RequestParam> headers;

	private List<RequestParam> pathParams;

	private List<RequestParam> queryParams;
}
