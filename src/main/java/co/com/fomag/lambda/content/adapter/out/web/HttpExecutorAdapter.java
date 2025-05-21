package co.com.fomag.lambda.content.adapter.out.web;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import co.com.fomag.lambda.content.application.domain.ConfigRequest;
import co.com.fomag.lambda.content.application.domain.HttpMethodType;
import co.com.fomag.lambda.content.application.domain.ProxyRequest;
import co.com.fomag.lambda.content.application.domain.ProxyResponse;
import co.com.fomag.lambda.content.application.domain.RequestParam;
import co.com.fomag.lambda.content.application.exceptions.ProxyLambdaException;
import co.com.fomag.lambda.content.application.ports.out.web.HttpExecutorPort;
import co.com.fomag.lambda.content.infraestructure.annotations.Adapter;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpHeaders;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.uri.UriBuilder;
import lombok.extern.slf4j.Slf4j;

@Adapter
@Slf4j
public class HttpExecutorAdapter implements HttpExecutorPort {

	private static final String OPENED_BRACKET = "{";

	private static final String CLOSED_BRACKET = "}";

	private static final String ALL_HEADERS = "*";

	private static final String HEADERS_SEPARATOR = "*||*";

	private static final String RESPONSE_HEADERS_SEPARATOR = ",";

	@Override
	public ProxyResponse executeRequest(ProxyRequest request, ConfigRequest config) {

		try {
			var httpClient = HttpClient.create(UriBuilder.of(config.getEndpoint()).build().toURL());
			var uri = UriBuilder.of(config.getEndpoint()).path(buildPath(request, config) + buildQueryParams(request))
					.build();

			log.info("Start calling rest service ... {}", uri);

			var httpRequest = buildRequest(request, config, uri);

			HttpResponse<String> httpResponse = httpClient.toBlocking().exchange(httpRequest, String.class);

			log.debug("Response body {}", httpResponse.body());

			log.info("End calling rest service ... ");
			return new ProxyResponse(httpResponse.getBody().orElse(null), uri.toString(),
					buildResponseHeaders(httpResponse, config), request, config);

		} catch (MalformedURLException e) {
			throw new ProxyLambdaException(500, e.getMessage(), e.getMessage(), e);
		}
	}

	private String buildPath(ProxyRequest request, ConfigRequest config) {
		var path = config.getPath();

		var pathParams = request.getPathParams();

		if (pathParams == null) {
			return path;
		}

		for (RequestParam param : pathParams) {
			var name = OPENED_BRACKET.concat(param.getName().trim()).concat(CLOSED_BRACKET);
			var value = param.getValue();
			path = path.replace(name, value);
		}

		return path;
	}

	private String buildQueryParams(ProxyRequest request) {
		var path = new StringBuilder();

		var queryParams = request.getQueryParams();

		if (queryParams == null) {
			return path.toString();
		}

		boolean firstQuery = true;

		for (RequestParam param : queryParams) {
			var name = param.getName().trim();
			var value = param.getValue();

			if (firstQuery) {
				path.append("?").append(name).append("=").append(value);
				firstQuery = false;
			} else {
				path.append("&").append(name).append("=").append(value);
			}
		}

		return path.toString();
	}

	private List<RequestParam> buildHeaders(ProxyRequest request, ConfigRequest config) {
		var requestHeaders = request.getHeaders();

		List<RequestParam> allHeaders = new ArrayList<>();

		if (requestHeaders != null) {
			allHeaders.addAll(requestHeaders);
		}

		var configHeaders = config.getAdditionalHeaders();

		if (configHeaders != null) {
			allHeaders.addAll(configHeaders);
		}

		return allHeaders;
	}

	private HttpRequest<?> buildRequest(ProxyRequest request, ConfigRequest config, URI uri) {

		var method = config.getMethodType();

		log.info("Start building request for method {}", method);

		var allHeaders = buildHeaders(request, config);

		if (HttpMethodType.GET.equals(method)) {
			return HttpRequest.GET(uri).headers(headers -> addHeaders(headers, allHeaders));
		}

		if (HttpMethodType.POST.equals(method)) {
			return HttpRequest.POST(uri, request.getBody()).headers(headers -> addHeaders(headers, allHeaders));
		}

		if (HttpMethodType.PUT.equals(method)) {
			return HttpRequest.PUT(uri, request.getBody()).headers(headers -> addHeaders(headers, allHeaders));
		}

		if (HttpMethodType.PATCH.equals(method)) {
			return HttpRequest.PATCH(uri, request.getBody()).headers(headers -> addHeaders(headers, allHeaders));
		}

		if (HttpMethodType.DELETE.equals(method)) {
			return HttpRequest.DELETE(uri, request.getBody()).headers(headers -> addHeaders(headers, allHeaders));
		}

		return null;

	}

	private void addHeaders(MutableHttpHeaders headers, List<RequestParam> allHeaders) {
		for (RequestParam param : allHeaders) {
			headers.add(param.getName(), param.getValue());
			log.debug("header name {}, value {}", param.getName(), param.getValue());
		}
	}

	private List<RequestParam> buildResponseHeaders(HttpResponse<String> httpResponse, ConfigRequest config) {

		var requiredResponseHeaders = config.getResponseHeaders();

		var headers = httpResponse.getHeaders().asMap();

		return buildResponseHeaders(headers, requiredResponseHeaders);
	}

	private List<RequestParam> buildResponseHeaders(Map<String, List<String>> headers,
			List<String> requiredResponseHeaders) {

		List<RequestParam> responseHeaders = new ArrayList<>();

		if (requiredResponseHeaders == null || requiredResponseHeaders.isEmpty()) {
			return responseHeaders;
		}

		if (headers.isEmpty()) {
			return responseHeaders;
		}

		boolean containsAllHeaders = requiredResponseHeaders.contains(ALL_HEADERS);

		log.debug("contains all headers, {}", containsAllHeaders);

		headers.forEach((key, value) -> {
			var header = new RequestParam();

			if (!(containsAllHeaders || requiredResponseHeaders.contains(key))) {
				return;
			}

			log.debug("Header from response {}", key);

			header.setName(key);

			var sb = new StringBuilder();

			value.forEach(val -> sb.append(val).append(HEADERS_SEPARATOR));

			var valueAsString = sb.toString();

			if (valueAsString.contains(HEADERS_SEPARATOR)) {
				valueAsString = valueAsString.substring(0, valueAsString.lastIndexOf(HEADERS_SEPARATOR));
				valueAsString = valueAsString.replace(HEADERS_SEPARATOR, RESPONSE_HEADERS_SEPARATOR);
			}

			header.setValue(valueAsString);

			responseHeaders.add(header);
		});

		return responseHeaders;
	}
}
