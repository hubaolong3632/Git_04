package 生产者;

import com.rabbitmq.client.Channel;

import java.io.IOException;

class Producerq {

    public void podUcer(String src, Channel channel) throws IOException {

        //发送消息
        channel.basicPublish("", "sendmail", null, src.getBytes()); //消息发送
        System.out.println("邮件发送成功!,请稍后！（单次时间10秒）");


    }


}
