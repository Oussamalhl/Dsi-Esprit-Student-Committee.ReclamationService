package dsi.esprit.tn.services;

import dsi.esprit.tn.Models.Reclamation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class EmailingServiceImpl implements IEmailingServiceImpl {

    @Value("${spring.mail.username}")
    private String serverMail;
    @Autowired
    private JavaMailSender emailSender;

    static String TMP_UPLOAD_FOLDER ="E:\\Esprit DSI\\dsi.esprit.reclamationService\\reclamationservice\\tmp\\reclamation\\";


    public void ReclamationSentMail(List<String> user, Reclamation r) throws Exception {

        // Mail Reclamation
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        MimeMessage mm = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mm, true); // true = multipart
        String Template = "<!DOCTYPE html>\n" +
                "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
                "<head>\n" +
                "  <meta charset=\"UTF-8\">\n" +
                "  <meta name=\"viewport\" content=\"width=device-width,initial-scale=1\">\n" +
                "  <meta name=\"x-apple-disable-message-reformatting\">\n" +
                "  <title></title>\n" +
                "  <!--[if mso]>\n" +
                "  <noscript>\n" +
                "    <xml>\n" +
                "      <o:OfficeDocumentSettings>\n" +
                "        <o:PixelsPerInch>96</o:PixelsPerInch>\n" +
                "      </o:OfficeDocumentSettings>\n" +
                "    </xml>\n" +
                "  </noscript>\n" +
                "  <![endif]-->\n" +
                "  <style>\n" +
                "    table, td, div, h1, p {font-family: Arial, sans-serif;}\n" +
                "  </style>\n" +
                "</head>\n" +
                "<body style=\"margin:0;padding:0;\">\n" +
                "  <table role=\"presentation\" style=\"width:100%;border-collapse:collapse;border:0;border-spacing:0;background:#ffffff;\">\n" +
                "    <tr>\n" +
                "      <td align=\"center\" style=\"padding:0;\">\n" +
                "        <table role=\"presentation\" style=\"width:602px;border-collapse:collapse;border:1px solid #cccccc;border-spacing:0;text-align:left;\">\n" +
                "          <tr>\n" +
                "            <td align=\"center\" style=\"padding:40px 0 30px 0;background:#ee4c50;\">\n" +
                "              <img src='cid:esprit' alt=\"\" width=\"300\" style=\"height:auto;display:block;\" />\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "          <tr>\n" +
                "            <td style=\"padding:36px 30px 42px 30px;\">\n" +
                "              <table role=\"presentation\" style=\"width:100%;border-collapse:collapse;border:0;border-spacing:0;\">\n" +
                "                <tr>\n" +
                "                  <td style=\"padding:0 0 36px 0;color:#153643;\">\n" +
                "                    <h1 style=\"font-size:24px;margin:0 0 20px 0;font-family:Arial,sans-serif;\">"+r.getName()+"</h1>\n" +
                "                    <p style=\"margin:0 0 12px 0;font-size:16px;line-height:24px;font-family:Arial,sans-serif;\">"+
                                        "Hello " + user.get(2) + " " + user.get(3) + "," + "\n \n"
                                        + "Your Reclamation <b>"+r.getName()+"</b> of the following target, <b>"+r.getTarget()
                                        +"</b> submitted at "+dateFormat.format(r.getDate())
                                        +" has been successfully sent."
                                        +"<br>Reclamation description: "+r.getDescription()
                                        + "<br> You will find your reclamations on the link below.\n"
                                        + "<br> Reclamation Link: "
                                        + "<br> Thank you for expressing your opinions and ideas to us. We look forward to hearing from you again.\n\n"
                                        + "<br> Regards," + "The DSI ESPRIT Team"+
                "                    <p style=\"margin:0;font-size:16px;line-height:24px;font-family:Arial,sans-serif;\">" +
                "                      <a href=\"https://www.dsi.esprit.tn/Reclamations/\" style=\"color:#ee4c50;text-decoration:underline;\">"+r.getName()+"</a></p>\n" +
                "                  </td>\n" +
                "                </tr>\n" +
                "              </table>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "          <tr>\n" +
                "            <td style=\"padding:30px;background:#ee4c50;\">\n" +
                "              <table role=\"presentation\" style=\"width:100%;border-collapse:collapse;border:0;border-spacing:0;font-size:9px;font-family:Arial,sans-serif;\">\n" +
                "                <tr>\n" +
                "                  <td style=\"padding:0;width:50%;\" align=\"left\">\n" +
                "                    <p style=\"margin:0;font-size:14px;line-height:16px;font-family:Arial,sans-serif;color:#ffffff;\">\n" +
                "                      &reg; Someone, Somewhere 2024<br/><a href=\"http://www.example.com\" style=\"color:#ffffff;text-decoration:underline;\">Unsubscribe</a>\n" +
                "                    </p>\n" +
                "                  </td>\n" +
                "                  <td style=\"padding:0;width:50%;\" align=\"right\">\n" +
                "                    <table role=\"presentation\" style=\"border-collapse:collapse;border:0;border-spacing:0;\">\n" +
                "                      <tr>\n" +
                "                        <td style=\"padding:0 0 0 10px;width:38px;\">\n" +
                "                          <a href=\"http://www.twitter.com/\" style=\"color:#ffffff;\"><img src=\"https://assets.codepen.io/210284/tw_1.png\" alt=\"Twitter\" width=\"38\" style=\"height:auto;display:block;border:0;\" /></a>\n" +
                "                        </td>\n" +
                "                        <td style=\"padding:0 0 0 10px;width:38px;\">\n" +
                "                          <a href=\"http://www.facebook.com/\" style=\"color:#ffffff;\"><img src=\"https://assets.codepen.io/210284/fb_1.png\" alt=\"Facebook\" width=\"38\" style=\"height:auto;display:block;border:0;\" /></a>\n" +
                "                        </td>\n" +
                "                      </tr>\n" +
                "                    </table>\n" +
                "                  </td>\n" +
                "                </tr>\n" +
                "              </table>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </table>\n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </table>\n" +
                "</body>\n" +
                "</html>";


        helper.setFrom(serverMail);
        helper.setTo(user.get(1));
        helper.setText(Template,true);
        helper.setSubject("Reclamation "+r.getName());

        FileSystemResource res = new FileSystemResource(new File(TMP_UPLOAD_FOLDER+"esprit.jpg"));
        helper.addInline("esprit", res);

        emailSender.send(mm);

    }


}
