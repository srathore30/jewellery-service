package com.jewellery.email;

import com.jewellery.constant.ApiErrorCodes;
import com.jewellery.exception.NoSuchElementFoundException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private JavaMailSender javaMailSender;

    public void sendPasswordResetEmail(String toEmail, String userName) {
        try {
            String subject = "Password Reset Notification";
            String messageBody = "Dear " + userName + ",\n\n" + "Your password has been successfully reset.";
            sendEmail(toEmail, subject, messageBody);
        } catch (Exception e) {
            throw new NoSuchElementFoundException(ApiErrorCodes.ERROR_WHILE_SENDING_EMAIL.getErrorCode(), ApiErrorCodes.ERROR_WHILE_SENDING_EMAIL.getErrorMessage());
        }
    }

    public void sendEmail(String toEmail, String subject, String msg) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(msg);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new NoSuchElementFoundException(ApiErrorCodes.ERROR_WHILE_SENDING_EMAIL.getErrorCode(), ApiErrorCodes.ERROR_WHILE_SENDING_EMAIL.getErrorMessage());
        }
    }
    public void sendEmailOtp(String otp, String toEmail){
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(toEmail);
            helper.setSubject("Email verification");
            helper.setText("Verification code for your account is " + otp);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new NoSuchElementFoundException(ApiErrorCodes.ERROR_WHILE_SENDING_EMAIL.getErrorCode(), ApiErrorCodes.ERROR_WHILE_SENDING_EMAIL.getErrorMessage());
        }
    }
}
