package mail.spring;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import java.util.Map;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Template;

/**
 * Author: Johnny
 * Date: 2016/12/9
 * Time: 20:38
 */
public class TemplateEmail {

    //TODO
    private JavaMailSender sender;
    private FreeMarkerConfigurer freeMarkerConfigurer = null;//FreeMarker的技术类

    public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
        this.freeMarkerConfigurer = freeMarkerConfigurer;
    }

    public void setSender(JavaMailSender sender) {
        this.sender = sender;
    }

    /**
     * 生成html模板字符串
     *
     * @param root 存储动态数据的map
     * @return
     */
    private String getMailText(Map<String, Object> root, String templateName) {
        String htmlText = "";
        try {
            //通过指定模板名获取FreeMarker模板实例
            Template tpl = freeMarkerConfigurer.getConfiguration().getTemplate(templateName);
            htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(tpl, root);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return htmlText;
    }

    /**
     * 发送邮件
     *
     * @param root    存储动态数据的map
     * @param toEmail 邮件地址
     * @param subject 邮件主题
     * @return
     */
    public boolean sendTemplateMail(Map<String, Object> root, String toEmail, String subject, String templateName) {
        try {
            MimeMessage msg = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg, false, "utf-8");//由于是html邮件，不是mulitpart类型
            helper.setFrom("admin@xxx.com");
            helper.setTo(toEmail);
            helper.setSubject(subject);
            String htmlText = getMailText(root, templateName);//使用模板生成html邮件内容
            helper.setText(htmlText, true);
            sender.send(msg);
            //System.out.println("成功发送模板邮件");
            return true;
        } catch (MailException e) {
            // System.out.println("失败发送模板邮件");
            e.printStackTrace();
            return false;
        } catch (MessagingException e) {
            //  System.out.println("失败发送模板邮件");
            e.printStackTrace();
            return false;
        }

    }
}
