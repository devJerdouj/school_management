package org.techie.notificationservice.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.LocalDate;
import java.util.HashMap;

import static java.nio.charset.StandardCharsets.*;
import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_RELATED;
import static org.techie.notificationservice.service.EmailTemplates.*;

@Service
@Slf4j
@AllArgsConstructor
public class EmailService {

    private JavaMailSender mailSender;
    private SpringTemplateEngine templateEngine;

    @Async
    public void sendPaymentComplete(
            String to, String fullName,
            String studentFullName,
            Double amount, Long paymentPhaseId) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, MULTIPART_MODE_RELATED, UTF_8.name());
        mimeMessageHelper.setFrom("info@school.org");

        final String template = PAYMENT_COMPLETE.getTemplate();
        mimeMessageHelper.setSubject(PAYMENT_COMPLETE.getSubject());

        var objects = new HashMap<String, Object>();
        objects.put("fullName", fullName);
        objects.put("amount", amount);
        objects.put("paymentPhaseId", paymentPhaseId);
        objects.put("studentFullName", studentFullName);

        Context context = new Context();
        context.setVariables(objects);
        try {
            String htmlTemplate = templateEngine.process(template, context);
            mimeMessageHelper.setText(template, true);
            mimeMessageHelper.setTo(to);
            mailSender.send(mimeMessage);
            log.info(String.format("INFO - Email successfully sent to %s with template %s ", to, template));
        }catch (Exception e) {
            log.warn("failed to send Email to : {} !", to);
        }
    }

    @Async
    public void sendNextPaymentAlert(String to, String fullName,
                                     String studentFullName, Double amount,
                                     LocalDate dueDate, Long paymentPhaseId) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, MULTIPART_MODE_RELATED, UTF_8.name());
        mimeMessageHelper.setFrom("info@school.org");

        mimeMessageHelper.setSubject(NEXT_PAYMENT_ALERT.getSubject());

        final String template = NEXT_PAYMENT_ALERT.getTemplate();

        var objects = new HashMap<String, Object>();
        objects.put("fullName", fullName);
        objects.put("amount", amount);
        objects.put("paymentPhaseId", paymentPhaseId);
        objects.put("dueDate", dueDate);
        objects.put("studentFullName", studentFullName);

        Context context = new Context();
        context.setVariables(objects);
        try {
            String htmlTemplate = templateEngine.process(template, context);
            mimeMessageHelper.setText(template, true);
            mimeMessageHelper.setTo(to);
            mailSender.send(mimeMessage);
            log.info(String.format("INFO - Email successfully sent to %s with template %s ", to, template));
        }catch (Exception e) {
            log.warn("failed to send Email to : {} !", to);
        }
    }

    @Async
    public void sendPaymentOverdueAlert(String to, String fullName,
                                     String studentFullName, Double amount,
                                     LocalDate dueDate, Long paymentPhaseId) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, MULTIPART_MODE_RELATED, UTF_8.name());
        mimeMessageHelper.setFrom("info@school.org");

        mimeMessageHelper.setSubject(PAYMENT_OVERDUE_ALERT.getSubject());

        final String template = PAYMENT_OVERDUE_ALERT.getTemplate();

        var objects = new HashMap<String, Object>();
        objects.put("fullName", fullName);
        objects.put("amount", amount);
        objects.put("paymentPhaseId", paymentPhaseId);
        objects.put("dueDate", dueDate);
        objects.put("studentFullName", studentFullName);

        Context context = new Context();
        context.setVariables(objects);
        try {
            String htmlTemplate = templateEngine.process(template, context);
            mimeMessageHelper.setText(template, true);
            mimeMessageHelper.setTo(to);
            mailSender.send(mimeMessage);
            log.info(String.format("INFO - Email successfully sent to %s with template %s ", to, template));
        }catch (Exception e) {
            log.warn("failed to send Email to : {} !", to);
        }
    }
}
