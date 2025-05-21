package co.com.fomag.lambda.content.application.services;

import co.com.fomag.lambda.content.application.domain.BrokerMessage;
import co.com.fomag.lambda.content.application.domain.ProxyRequest;
import co.com.fomag.lambda.content.application.domain.ProxyResponse;
import co.com.fomag.lambda.content.application.ports.in.execute.ExecuteRequestUseCase;
import co.com.fomag.lambda.content.application.ports.out.broker.BrokerMessageSenderPort;
import co.com.fomag.lambda.content.application.ports.out.environment.EnvironmentVariablesPort;
import co.com.fomag.lambda.content.application.ports.out.persistence.QueryConfigurationPort;
import co.com.fomag.lambda.content.application.ports.out.web.HttpExecutorPort;
import co.com.fomag.lambda.content.infraestructure.annotations.UseCase;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class ExecuteRequestService implements ExecuteRequestUseCase {

	private final QueryConfigurationPort queryConfigurationPort;

	private final HttpExecutorPort httpExecutorPort;

	private final BrokerMessageSenderPort brokerMessageSenderPort;

	private final EnvironmentVariablesPort environmentVariablesPort;

	@Override
	public ProxyResponse execute(ProxyRequest request) {

		log.info("Start request service ...");

		var config = queryConfigurationPort.query(request.getId(), request.getOperation());

		log.info("Configuration for id {}, operation  {}, ... {}", request.getId(), request.getOperation(), config);

		var response = httpExecutorPort.executeRequest(request, config);

		sendMessageToBroker(response);

		return response;
	}

	private void sendMessageToBroker(ProxyResponse response) {
		try {

			var message = new BrokerMessage();
			message.setEndpoint(response.getFullEnpoint());
			message.setOperation(response.getConfigRequest().getOperation());
			message.setOperationId(response.getConfigRequest().getId());

			brokerMessageSenderPort.sendMessage(StaticBuilder.mapper.writeValueAsString(message),
					environmentVariablesPort.getMonitorQueue());
		} catch (JsonProcessingException e) {
			log.error(e.getMessage(), e);
		}
	}
}
