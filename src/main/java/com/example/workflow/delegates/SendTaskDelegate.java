package com.example.workflow.delegates;

import com.example.workflow.model.Invitation;
import com.example.workflow.service.MailService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import javax.inject.Named;
import java.util.List;

@Named("sendTask")
public class SendTaskDelegate implements JavaDelegate {
    public SendTaskDelegate(MailService mailService) {
        this.mailService = mailService;
    }

    private final MailService mailService;


    @Override
    public void execute(DelegateExecution execution) throws Exception {
        List<Invitation> invitationList = ((List<Invitation>) execution.getVariable("invitationList"));

        invitationList.forEach((i)->{
            String text = "<p><span>Радистка Кэт вела Штирлица через темный двор.</span></p>\n" +
                    "<p><span>\"Не споткнись об пса,\" - сказала Кэт.</span></p>\n" +
                    "<p><span>Штирлиц споткнулся, раздался кошачий визг.</span></p>\n" +
                    "<p><span>\"<a href = 'http://localhost:8083/api/app/confirmInvitation/" + i.getHash() + "'>Об манула</a>\", - подумал Штирлиц</span></p>";
            mailService.sendMail(i.getParticipation().getSpeaker().getEmail(), text, true);
        });
    }

}