package co.com.fomag.lambda.system.application.domain;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@RequiredArgsConstructor
public class ProxyResponse {

	private final String body;
	
	private final String fullEnpoint;

	private final List<RequestParam> headers;
	
	private final ProxyRequest proxyRequest;
	
	private final ConfigRequest configRequest;
}
