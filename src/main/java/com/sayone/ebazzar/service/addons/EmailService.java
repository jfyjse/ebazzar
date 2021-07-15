package com.sayone.ebazzar.service.addons;

import com.sayone.ebazzar.entity.OrderEntity;
import com.sayone.ebazzar.entity.ProductEntity;
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
    JavaMailSender mailSender;

    public void sendOrderConfirmedEmail(OrderEntity orderEntity,
                                        String url) throws MessagingException, UnsupportedEncodingException {
        String toAddress = orderEntity.getCartEntity().getUserEntity().getEmail();
        String fromAddress = "merryjoseph00@gmail.com";
        String senderName = "E-BAZZAR";
        String subject = "Your Order is [[status]]";
        String content = "Dear [[name]],<br>"
                + " <b>Your Order is [[status]].</b><br>"
                + "Please Find below the details :<br>"
                + "<p>Order Id :</p>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">[[orderId]]</a></h3>"

                + "Thank you for Shopping with us.. <br>"
                + "EBAZZAR";


        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        subject = subject.replace("[[status]]", orderEntity.getOrderStatus());
        helper.setSubject(subject);


        content = content.replace("[[status]]", orderEntity.getOrderStatus());
        content = content.replace("[[name]]", orderEntity.getCartEntity().getUserEntity().getFirstName()+" "
                                                            +orderEntity.getCartEntity().getUserEntity().getLastName());
        String verifyURL = url + "/orders/" + orderEntity.getOrderId().toString();

        content = content.replace("[[URL]]", verifyURL);
        content = content.replace("[[orderId]]", orderEntity.getOrderId().toString());

        helper.setText(content, true);

        mailSender.send(message);

        System.out.println("Order Confirmation Mail Sent.............");
    }


}
