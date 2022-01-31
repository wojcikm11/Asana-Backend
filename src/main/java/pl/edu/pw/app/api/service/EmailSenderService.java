package pl.edu.pw.app.api.service;

public interface EmailSenderService {

    void send (String to, String email);
     String buildEmail(String name, String link,String msg,String actionMsg,String linkMsg);
}
