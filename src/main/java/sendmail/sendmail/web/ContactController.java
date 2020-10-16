package sendmail.sendmail.web;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sendmail.sendmail.dao.ContactRepository;
import sendmail.sendmail.model.Contact;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ContactController {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private ContactRepository contactRepository;
    @GetMapping("/")
    public String index(){
        return "index";
    }
    @RequestMapping("/contact")
    public String sendMail(HttpServletRequest request){
        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String subject = request.getParameter("subject");
        String content = request.getParameter("content");

        SimpleMailMessage message = new SimpleMailMessage();
        //Enrégistrement du contact en base de données
        Contact contact = new Contact(null,fullname,email,subject,content);
        contactRepository.save(contact);
        ///
        message.setFrom("test@3iweb.org");
        message.setTo(email);

        String mailSubject = fullname+ " has sent a message";
        String mailContent = "Sender Name: "+ fullname+"\n";
        mailContent+="Sender E-mail: "+email+"\n";
        mailContent+="Subject : "+subject+"\n";
        mailContent+="Content : "+content+"\n";

        message.setSubject(mailSubject);
        message.setText(mailContent);

        mailSender.send(message);

        return "message";
    }
    @GetMapping("/contacts")
    public String all(Model model){
        List<Contact> contactss = contactRepository.findAll();
        model.addAttribute("contacs",contactss);
        return "contacts";
    }
}
