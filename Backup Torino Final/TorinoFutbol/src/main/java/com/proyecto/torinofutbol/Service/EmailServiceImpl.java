package com.proyecto.torinofutbol.Service;

import com.proyecto.torinofutbol.Controllers.EmailController;
import jakarta.mail.internet.MimeMessage;
import org.antlr.v4.runtime.RuleContextWithAltNum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService{

    @Value("${spring.mail.username}")
    private String remitente;

    private String destinatario = "manuel.em.pedro@gmail.com";

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void enviarEmail(String asunto, String body) {
        try{
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(remitente);
            helper.setTo(destinatario);
            helper.setSubject(asunto);
            helper.setText(body);

            javaMailSender.send(mimeMessage);
//            return "Mail enviado";

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
