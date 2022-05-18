package com.example.workflow.service.impl;

import com.example.workflow.service.MailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MailServiceImpl implements MailService {
    @Override
    public void sendMail(String email, String text) {

    }

    @Override
    public void sendMail(String email, String text, boolean isHTML) {

    }
//    private final KafkaTemplate<String, MailMessage> kafkaTemplate;
//
//    public MailServiceImpl(KafkaTemplate<String, MailMessage> kafkaTemplate) {
//        this.kafkaTemplate = kafkaTemplate;
//    }
//
//    @Override
//    public void sendMail(String email, String text){
//        sendMail(email, text, false);
//    }
//
//    @Override
//    @Transactional(transactionManager = "kafkaTransactionManager")
//    public void sendMail(String email, String text, boolean isHTML){
//        MailMessage msg = new MailMessage();
//
//        msg.setEmail(email);
//        msg.setText(text);
//        msg.setHTML(isHTML);
//        kafkaTemplate.send("mail-topic", msg);
//    }
}
