package com.zealmobile.studygroup.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private final JavaMailSender _mailSender;
    public EmailService(JavaMailSender mailSender) {
        super();
        _mailSender = mailSender;
    }
    

    public boolean sendEmail(SimpleMailMessage message) {
        try{
           // message.setFrom("simon.dev.bassey@gmail.com");
            message.setReplyTo(message.getFrom());
            message.setSentDate(new Date());
            _mailSender.send(message);
            return true;
        }
        catch (Exception exception){
            //Todo: log excxeption
            return false;
        }
    }
    
}
