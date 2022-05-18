package com.example.workflow.delegates;

import com.example.workflow.dto.MailMessage;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.kafka.core.KafkaTemplate;

import javax.inject.Named;

@Named("sendTask")
public class SendTaskDelegate implements JavaDelegate {
    private final KafkaTemplate<String, MailMessage> kafkaTemplate;

    public SendTaskDelegate(KafkaTemplate<String, MailMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void execute(DelegateExecution execution) throws Exception {
        MailMessage msg = new MailMessage();

        msg.setEmail("email");
        msg.setText("text");
        msg.setHTML(false);

        kafkaTemplate.send("mail-topic", msg);
    }

}