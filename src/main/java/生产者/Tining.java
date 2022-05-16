        package 生产者;

        import com.rabbitmq.client.Channel;
        import org.quartz.Job;
        import org.quartz.JobExecutionContext;
        import org.quartz.JobExecutionException;
        import util.Result;

        import javax.xml.crypto.Data;
        import java.io.IOException;
        import java.text.SimpleDateFormat;
        import java.util.Date;
        import java.util.concurrent.ExecutorService;
        import java.util.concurrent.Executors;

        public class Tining  implements Job {
          private static Producerq p1=new Producerq();
          public static   Channel channel;
          public static ExecutorService execut= Executors.newFixedThreadPool(20); //创建线程池
            @Override
            public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        int  a=0;
                for (Result result : Fasinfuwuqi.result) {
                        execut.submit(() -> {
                            try {
                                System.out.println("当前线程"+Thread.currentThread().getName());
                                String convert = JsonZH.convert(result);
                                p1.podUcer(convert,channel);
                            } catch (IOException e) {
                                System.out.println("注册失败");
                            }
                        });
                    a++;
                }

        //        Fasinfuwuqi.result.clear(); //清空集合
                Date date = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
                System.out.println("执行时间为：" + dateFormat.format(date)+"   执行次数:" +a  );

            }
        }
