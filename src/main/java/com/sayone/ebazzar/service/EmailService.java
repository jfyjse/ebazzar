package com.sayone.ebazzar.service;

import com.sayone.ebazzar.entity.PasswordResetTokenEntity;
import com.sayone.ebazzar.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleEmailForPasswordReset(
            PasswordResetTokenEntity passwordResetTokenEntity, UserEntity userEntity, String url)
            throws MessagingException, UnsupportedEncodingException {

        String toAddress = passwordResetTokenEntity.getUserDetails().getEmail();
        String fromAddress = "demouser3101@gmail.com";
        String senderName = "Ebazzar";
        String subject = "Please reset your password here";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to reset your password:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">RESET</a></h3>"
                + "Thank you,<br>"
                + "Your company name.";


        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", passwordResetTokenEntity.getUserDetails().getFirstName());
        String resetURL = url + "/users/"+userEntity.getEmail()+"/resetpassword?token=" + passwordResetTokenEntity.getToken();

        content = content.replace("[[URL]]", resetURL);

        helper.setText(content, true);

        mailSender.send(message);

        System.out.println("Password Reset Mail Sent.............");
    }
}
