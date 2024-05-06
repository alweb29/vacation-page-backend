package com.example.vacationpagebackend.service;

import com.example.vacationpagebackend.controller.Mail;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSenderService {

    private final JavaMailSender mailSender;

    public boolean sendEmail(Mail mail){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("albertow55@gmail.com");
        simpleMailMessage.setSubject("Rezerwacja Apartamenty Turkusowe");
        simpleMailMessage.setTo("albertow55@gmail.com");
        simpleMailMessage.setText(mail.toString());

        try {
            mailSender.send(simpleMailMessage);
            System.out.println("Email sent");
            return true;
        } catch (MailException e) {
            System.out.println("Error sending email" + e);
            return false;
        }
    }
}
