package 没啥大用的东西;

import Json.JsonConverter;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import util.Result;


import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Yjfs {

    public static void main(String[] args) {
        Producer p1=new Producer();
        Scanner sc = new Scanner(System.in);
        String expr = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})$";

        while (true){
            System.out.println("请输入认证qq邮箱号");
            String next = sc.next();

                try {
                    if(next.matches(expr)){
                        String convert = JsonConverter.convert(new Result(next));
                        p1.podUcer(convert);
                    }else{
                        System.out.println("邮件格式不正确");
                    }
                } catch (Exception e) {
                    System.out.println("邮件发送失败");
                }

        }}
}

class Producer {

    public void podUcer(String src) throws IOException, TimeoutException {
        ConnectionFactory fact=new ConnectionFactory();
        fact.setHost("127.0.0.1"); //设置ip
        fact.setPort(5672); //设置端口
        fact.setVirtualHost("/itcast"); //设置对应的虚拟机
        fact.setUsername("admin");//用户名
        fact.setPassword("admin"); //密码
        //创建连接Connection
        Connection cont = fact.newConnection();
        //创建Channel
        Channel channel = cont.createChannel();


        //创建队列
        channel.queueDeclare("sendmail",true,false,false,null); //见图

        //发送消息
        channel.basicPublish("","sendmail",null,src.getBytes()); //消息发送


        channel.close();
        cont.close(); //关闭

        }
}

