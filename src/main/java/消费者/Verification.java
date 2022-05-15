package 消费者;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

public class Verification {
    private static String sender = "911412667@qq.com";//寄件人
    private static String Authorization = "iiljcvxpderebcci"; //授权码
    private static String myEmailSMTPHost = "smtp.qq.com";//qq的服务器
    private static Session s1;

    public String read() { //生成随机码
        Random r = new Random();
        StringBuilder rs = new StringBuilder();
        for (int i = 0; i < 7; i++) {  //7位随机数
            rs.append(r.nextInt(10));
        }
        return String.valueOf(rs);
    }

    static {
        Properties ps = new Properties();
        ps.setProperty("mail.transport.protocol", "smtp");
        ps.setProperty("mail.smtp.host", myEmailSMTPHost);
        ps.setProperty("mail.smtp.auth", "true");

        s1 = Session.getInstance(ps);
        s1.setDebug(true); //可查看bug问题

    }

    public void send(String recipient) throws Exception { //发送邮件

        MimeMessage msg = mailSending(s1, recipient);
        Transport tport = s1.getTransport();
        tport.connect(sender, Authorization); //qq号和密码
        tport.sendMessage(msg, msg.getAllRecipients()); //发送
        tport.close();
    }

    public MimeMessage mailSending(Session s1, String recipient) throws Exception {       // 邮件发送
        MimeMessage msg = new MimeMessage(s1);
        msg.setFrom(new InternetAddress(sender, "验证码", "UTF-8")); //发件人

        msg.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(recipient, "验证码接收用户", "UTF-8"));

        msg.setSubject("服务验证码");

        msg.setContent("验证码为:" + read(), "text/html;charset=UTF-8"); //发送的邮件
        Date date = new Date();
        msg.setSentDate(date);

        msg.saveChanges();//保存设置

        return msg;

    }


}
