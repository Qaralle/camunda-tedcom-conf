package com.example.workflow.delegates.adapter;

import com.example.workflow.dto.MailMessage;
import com.example.workflow.util.KafkaMessageAdapterMatcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.MismatchingMessageCorrelationException;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.rest.dto.message.MessageCorrelationResultDto;
import org.camunda.bpm.engine.runtime.MessageCorrelationBuilder;
import org.camunda.bpm.engine.runtime.MessageCorrelationResult;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import spinjar.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class CamundaMessageAdapter {
    private final static String MESSAGE_START = "StartMessage";
    private final RuntimeService runtimeService;

    @KafkaListener(topics = "mail-topic", groupId = "mail-service")
    public void startProcessListener(MailMessage msg){
        correlateMessage(msg,MESSAGE_START);
    }

    protected void correlateMessage(MailMessage msg, String messageName){
        try {
            log.info("Consuming message {}", messageName);

            MessageCorrelationBuilder messageCorrelationBuilder = runtimeService.createMessageCorrelation(messageName);

            Map<String, Object> variables = KafkaMessageAdapterMatcher.toVariableMap(msg);
            messageCorrelationBuilder.setVariables(variables);

            MessageCorrelationResult messageResult = messageCorrelationBuilder.correlateWithResult();

            String messageResultJson = new ObjectMapper()
                    .writeValueAsString(MessageCorrelationResultDto.fromMessageCorrelationResult(messageResult));

            log.info("Correlation successful. Process Instance Id: {}", messageResultJson);

        } catch (MismatchingMessageCorrelationException e) {
            log.error("Issue when correlating the message: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Unknown issue occurred", e);
        }
    }

}
