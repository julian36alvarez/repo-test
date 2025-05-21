package co.com.fomag.lambda.content.application.domain;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ConfigRequest {

	private String id;

	private String operation;

	private String endpoint;
	
	private String path;

	private HttpMethodType methodType;

	private AuthType authType;

	private String authParameterName;

	private List<RequestParam> additionalHeaders;
	
	private List<String> responseHeaders;
}
