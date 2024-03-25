package doris.dorisaccountservice.service;

import java.util.Map;

public interface EmailService {
    public void sendEmailWithTemple(String to, String subject, String templateName, Map<String, Object> model);
}
