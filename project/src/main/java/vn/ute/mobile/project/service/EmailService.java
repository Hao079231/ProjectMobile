package vn.ute.mobile.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
  @Autowired
  private JavaMailSender mailSender;

  public void sendEmail(String email, String subject, String body){
    try {
      SimpleMailMessage message = new SimpleMailMessage();
      message.setFrom("haotrung123tes@gmail.com");
      message.setTo(email);
      message.setSubject(subject);
      message.setText(body);
      mailSender.send(message);
    } catch (Exception e){
      e.getMessage();
    }
  }
}
