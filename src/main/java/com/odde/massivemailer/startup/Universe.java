package com.odde.massivemailer.startup;

import com.odde.massivemailer.service.ConsentService;
import com.odde.massivemailer.service.GDPRService;
import com.odde.massivemailer.service.MailService;
import com.odde.massivemailer.service.TemplateService;

import java.util.function.Supplier;

import static com.google.common.base.Preconditions.checkNotNull;

public class Universe {
    private static volatile MailService mailService;
    private static volatile TemplateService templateService;
    private static volatile ConsentService consentService;
    private static volatile GDPRService gdprService;

    static void createUniverse() {
        mailService = createMailService();
        templateService = createTemplateService();
        consentService = createConsentService();
        gdprService = createGDPRService();
    }

    public static void createUniverse(MockConfiguration mockConfiguration) {
        mailService = firstNonNull(mockConfiguration.mailService, Universe::createMailService);
        templateService = firstNonNull(mockConfiguration.templateService, Universe::createTemplateService);
        consentService = firstNonNull(mockConfiguration.consentService, Universe::createConsentService);
        gdprService = firstNonNull(mockConfiguration.gdprService, Universe::createGDPRService);
    }

    public static GDPRService gdprService() {
        return gdprService;
    }

    public static MailService mailService() {
        return mailService;
    }

    public static TemplateService templateService() {
        return templateService;
    }

    public static ConsentService consentService() {
        return consentService;
    }

    private static GDPRService createGDPRService() {
        return new GDPRService(mailService(), templateService(), consentService());
    }

    private static ConsentService createConsentService() {
        return new ConsentService();
    }

    private static TemplateService createTemplateService() {
        return new TemplateService();
    }

    private static MailService createMailService() {
        return MailService.createMailService();
    }

    public static <T> T firstNonNull(T first, Supplier<T> second) {
        return first != null ? first : checkNotNull(second.get());
    }

    public static class MockConfiguration {
        private MailService mailService;
        private TemplateService templateService;
        private ConsentService consentService;
        private GDPRService gdprService;

        public MockConfiguration with(MailService mailService) {
            this.mailService = mailService;
            return this;
        }

        public MockConfiguration with(TemplateService templateService) {
            this.templateService = templateService;
            return this;
        }

        public MockConfiguration with(ConsentService consentService) {
            this.consentService = consentService;
            return this;
        }

        public MockConfiguration with(GDPRService gdprService) {
            this.gdprService = gdprService;
            return this;
        }
    }
}
