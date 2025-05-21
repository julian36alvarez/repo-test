package co.com.fomag.lambda.content.application.domain;

import io.micronaut.core.util.StringUtils;

public enum HttpMethodType {
	GET, POST, PUT, PATCH, DELETE;

	public static HttpMethodType getType(String name) {
		if (!StringUtils.hasText(name)) {
			return null;
		}

		for (HttpMethodType type : HttpMethodType.values()) {
			if (type.name().equals(name)) {
				return type;
			}
		}

		return null;
	}
}
