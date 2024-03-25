package doris.dorisaccountservice.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.internet.MimeMessage;
import lombok.NonNull;

@Service
public class EmailServiceImp implements EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public void sendEmailWithTemple(@NonNull String to, @NonNull String subject, String templateName, Map<String, Object> model) 
    {
        try
        {
            MimeMessage message = this.mailSender.createMimeMessage();
            Context context = new Context();

            context.setVariables(model);
            
            String htmlTemple = this.templateEngine.process(templateName, context);
            
            message.setRecipients(MimeMessage.RecipientType.TO, to);
            message.setSubject(subject);
            message.setContent(htmlTemple, "text/html; charset=utf-8");
    
            this.mailSender.send(message);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
