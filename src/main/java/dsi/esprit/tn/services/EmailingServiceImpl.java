package dsi.esprit.tn.services;

import dsi.esprit.tn.Models.Reclamation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class EmailingServiceImpl implements IEmailingServiceImpl {

    @Value("${spring.mail.username}")
    private String serverMail;
    @Autowired
    private JavaMailSender emailSender;

    static String TMP_UPLOAD_FOLDER ="E:\\Esprit DSI\\dsi.esprit.eventService\\eventservice\\tmp\\";


    public void ReclamationSentMail(List<String> user, Reclamation r) throws Exception {

        // Mail Reclamation
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        MimeMessage mm = emailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mm, true);
        mimeMessageHelper.setFrom(serverMail);
        mimeMessageHelper.setTo(user.get(1));
        mimeMessageHelper.setText("Hello " + user.get(2) + " " + user.get(3) + "," + "\n \n"
                + "Your Reclamation '"+r.getName()+"' of the following target, '"+r.getTarget()
                + "'\n Description:  " + r.getDescription() + "\nDate: \n" +dateFormat.format(r.getDate()) + "\n"
                + "You will find your reclamations on the link below.\n"
                + "\nReclamation Link: "
                + "https://www.dsi.esprit.tn/Reclamations/" + r.getName()+ ".\n"
                + "Thank you for expressing your opinions and ideas to us. We look forward to hearing from you again.\n\n"
                + "Regards,\n" + "The DSI ESPRIT Team");
        mimeMessageHelper.setSubject("Reclamation "+r.getName());


        emailSender.send(mm);

    }


}
