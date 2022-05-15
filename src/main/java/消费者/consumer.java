package 消费者;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class consumer {
    public static void main(String[] args) throws Exception {
        Verification v1=new Verification();

        //创建连接工厂
        ConnectionFactory fact=new ConnectionFactory();
        //设置参数
        fact.setHost("127.0.0.1"); //设置ip
        fact.setPort(5672); //设置端口
        fact.setVirtualHost("/itcast"); //设置对应的虚拟机
        fact.setUsername("admin");//用户名
        fact.setPassword("admin"); //密码
        Connection cont = fact.newConnection();        //创建连接Connection
        Channel channel = cont.createChannel();        //创建Channel


        ExecutorService es= Executors.newFixedThreadPool(10); //创建线程池


        Consumer consurme=new DefaultConsumer(channel){

            //回调方法 ,当收到消息后会自动执行该方法
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //消息的标识     获取一些<交换机信息,路由器Key信息>    配置信息   最后的数据
                try {
                    JSONObject js=new JSONObject().parseObject(new String(body));
                    String id = js.getString("id");

                    System.out.println("邮箱为："+id+"发送成功");
                    es.submit(() -> {  //通过线程池启动
                        try {
                            System.out.println("当前线程"+Thread.currentThread().getName());
                            v1.send(id);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });

                    System.out.println("当前暂停中");
                    Thread.sleep(5000); //下一次发送等待5秒
                    System.out.println("启动");

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };

        channel.basicConsume("sendmail",true,consurme); //获取



    }

}



