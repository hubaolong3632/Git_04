//package consumer;
//
//import com.alibaba.fastjson.JSONObject;
//import com.rabbitmq.client.*;
//
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//import java.io.IOException;
//import java.util.Date;
//import java.util.Properties;
//import java.util.Random;
//
//public class consumer2 {
//    public static void main(String[] args) throws Exception {
//        String a1="911412667@qq.com";
//        Verification1 v1=new Verification1();
//
//        //创建连接工厂
//        ConnectionFactory fact=new ConnectionFactory();
//
//        //设置参数
//        fact.setHost("1.117.77.47"); //设置ip
//        fact.setPort(5672); //设置端口
//        fact.setVirtualHost("server"); //设置对应的虚拟机
//        fact.setUsername("admin");//用户名
//        fact.setPassword("admin"); //密码
//
//        Connection cont = fact.newConnection();        //创建连接Connection
//        Channel channel = cont.createChannel();        //创建Channel
//
//
//        Consumer consurme=new DefaultConsumer(channel){
//
//            //回调方法 ,当收到消息后会自动执行该方法
//            @Override
//            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//                //消息的标识     获取一些<交换机信息,路由器Key信息>    配置信息   最后的数据
//                try {
//                    JSONObject js=new JSONObject().parseObject(new String(body));
//                    String id = js.getString("id");
//
//                    System.out.println("邮箱为："+id+"发送成功");
//                    v1.send(id);
//
//                    System.out.println("当前暂停中");
//                    Thread.sleep(5000); //下一次发送等待5秒
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        };
//
//        channel.basicConsume("sendmail",true,consurme); //获取
//
//
//
//    }
//
//}
//
//
//class  Verification1{
//    private  static String sender="911412667@qq.com";//寄件人
//    private  static String Authorization="vubmtifaclhfdjhi"; //授权码
//    private  static String myEmailSMTPHost = "smtp.qq.com";//qq的服务器
//    private  static Session s1;
//    public String read(){ //生成随机码
//        Random r = new Random();
//        StringBuilder rs = new StringBuilder();
//        for (int i = 0; i < 7; i++) {  //7位随机数
//            rs.append(r.nextInt(10));
//        }
//        return  String.valueOf(rs);
//    }
//
//    static {
//        Properties ps = new Properties();
//        ps.setProperty("mail.transport.protocol", "smtp");
//        ps.setProperty("mail.smtp.host", myEmailSMTPHost);
//        ps.setProperty("mail.smtp.auth", "true");
//
//
//        s1=Session.getInstance(ps);
//        s1.setDebug(true); //可查看bug问题
//
//
//    }
//    public void send(String recipient) throws Exception { //发送邮件
//
//        MimeMessage msg=mailSending(s1,recipient);
//        Transport tport= s1.getTransport();
//        tport.connect(sender,Authorization); //qq号和密码
//        tport.sendMessage(msg, msg.getAllRecipients()); //发送
//        tport.close();
//    }
//
//    public MimeMessage mailSending(Session s1,String recipient) throws Exception{       // 邮件发送
//        MimeMessage msg=new MimeMessage(s1);
//        msg.setFrom(new InternetAddress(sender,"验证码","UTF-8")); //发件人
//
//        msg.setRecipient(MimeMessage.RecipientType.TO,new InternetAddress(recipient,"验证码接收用户","UTF-8"));
//
//        msg.setSubject("服务验证码");
//
//        msg.setContent("验证码为:"+read(),"text/html;charset=UTF-8"); //发送的邮件
//        Date date = new Date();
//        msg.setSentDate(date);
//
//        msg.saveChanges();//保存设置
//
//        return msg;
//
//    }
//
//
//
//
//}
//
