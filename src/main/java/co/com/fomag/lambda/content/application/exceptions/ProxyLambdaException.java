package co.com.fomag.lambda.content.application.exceptions;

import lombok.Getter;

@Getter
public class ProxyLambdaException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final int code;

	private final String errorCode;

	private final String message;

	public ProxyLambdaException(int httpCode, String errorCode, String message) {
		super(message);
		this.code = httpCode;
		this.message = message;
		this.errorCode = errorCode;
	}

	public ProxyLambdaException(int httpCode, String errorCode, String message, Throwable throwable) {
		super(message, throwable);
		this.code = httpCode;
		this.message = message;
		this.errorCode = errorCode;
	}
}
