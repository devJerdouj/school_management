package org.techie.notificationservice.service;

import lombok.Getter;

@Getter
public enum EmailTemplates {

    PAYMENT_COMPLETE("payment-complete.html", "Payment successfully complete"),
    NEXT_PAYMENT_ALERT("payment-alert.html", "Next payment due date Alert");

    private final String template;
    private final String subject;


    EmailTemplates(String template, String subject) {
        this.template = template;
        this.subject = subject;
    }
}