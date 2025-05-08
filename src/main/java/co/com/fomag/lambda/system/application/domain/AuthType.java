package co.com.fomag.lambda.system.application.domain;

import io.micronaut.core.util.StringUtils;

public enum AuthType {
	NONE, API_KEY, OAUTH2;

	public static AuthType getType(String name) {
		if (!StringUtils.hasText(name)) {
			return null;
		}

		for (AuthType type : AuthType.values()) {
			if (type.name().equals(name)) {
				return type;
			}
		}

		return null;
	}
}
