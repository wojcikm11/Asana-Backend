package pl.edu.pw.domain.api.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService {



    private final static Logger LOGGER = LoggerFactory.getLogger(EmailSenderServiceImpl.class);
//    @Autowired
    private final JavaMailSender mailSender;

    @Override
    @Async
    public void send(String to, String email) {

        try{
            MimeMessage mimeMessage =mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,"utf-8");
            helper.setText(email,true);
            helper.setTo(to);
            helper.setSubject("Confirm your email");
            helper.setFrom("asanaCopy@pw.edu.pl");
            mailSender.send(mimeMessage);

        }catch(MessagingException e){
            LOGGER.error("Failed to send email",e);
            throw new IllegalStateException("Failed to send email");
        }

    }
}
