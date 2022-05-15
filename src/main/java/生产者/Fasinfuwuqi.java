package 生产者;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import util.Result;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

public class Fasinfuwuqi {
    public static List<Result> result=new ArrayList<>();

        public static void main(String[] args) throws IOException, TimeoutException, SchedulerException {
            Scanner sc = new Scanner(System.in);

            ConnectionFactory fact=new ConnectionFactory();
            fact.setHost("1.117.77.47"); //设置ip
            fact.setPort(5672); //设置端口
            fact.setVirtualHost("server"); //设置对应的虚拟机
            fact.setUsername("admin");//用户名
            fact.setPassword("admin"); //密码
            //创建连接Connection
            Connection cont = fact.newConnection();
            //创建Channel
            Channel channel = cont.createChannel();
            //创建队列
            channel.queueDeclare("sendmail",true,false,false,null); //见图
            ExecutorService es= Executors.newFixedThreadPool(10); //创建线程池

            String expr = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})$";
            ExecutorService execut= Executors.newFixedThreadPool(10); //创建线程池



            Tining.channel=channel;    //调度器（Scheduler）从工厂中获取调度的实例
            Scheduler defaultScheduler  = StdSchedulerFactory.getDefaultScheduler();
            //2.任务实例（JobDetail）
            JobDetail build = JobBuilder.newJob(Tining.class)//加载任务类 与HelloJob进行绑定 要求：HelloJob必须要实现job接口才可以
                    .withIdentity("job1", "group1") //参数1：任务的名称（唯一实例） 参数2：任务组的名称
                    .build();
            //3.触发器（Trigger）
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger1", "group1")//参数1：触发器的名称（唯一实例） 参数2：触发器组的名称
                    .withSchedule(CronScheduleBuilder.cronSchedule("0/10 * * * * ?"))
                    .build();
            //让调度器关联任务和触发器，保证按照触发器定义的条件执行任务
            defaultScheduler.scheduleJob(build,trigger);

            //启动
            defaultScheduler.start();


            while (true){
                System.out.println("请输入认证qq邮箱号");
                String next = sc.next();
                execut.submit(() -> {
                        if(next.matches(expr)){
                            result.add(new Result(next));
                        }else{
                            System.out.println("邮件格式不正确");
                        }

                });


            }
        }
    }

