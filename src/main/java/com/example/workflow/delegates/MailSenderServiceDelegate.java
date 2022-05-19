package com.example.workflow.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.inject.Named;
import javax.mail.internet.MimeMessage;

@Named("mailSenderService")
public class MailSenderServiceDelegate implements JavaDelegate {
    private final JavaMailSender javaMailSender;

    public MailSenderServiceDelegate(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        
        String text = (String) delegateExecution.getVariable("text");
        String email = (String) delegateExecution.getVariable("email");
        Boolean isHTML = (Boolean) delegateExecution.getVariable("HTML");

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setSubject("TED.COM NEEDS YOU");
        helper.setTo(email);
        helper.setText(text, isHTML);
        javaMailSender.send(message);
    }
}
