package com.example.workflow.delegates;

import com.example.workflow.service.NotificationService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import javax.inject.Named;

@Named("notifier")
public class NotifierDelegate implements JavaDelegate {
    private final NotificationService notificationService;

    public NotifierDelegate(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        notificationService.eveningNotification();
    }
}
