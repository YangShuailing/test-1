package com.johnny.rabbitmq.component;

import com.alibaba.fastjson.JSONObject;
import com.johnny.rabbitmq.model.MailMessageModel;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

@Component("mailMessageListenerAdapter")
public class MailMessageListenerAdapter extends MessageListenerAdapter {

    @Resource
    private JavaMailSender mailSender;

    @Value("${mail.username}")
    private String mailUsername;

    /**
     1. 从 RabbitMQ 的消息队列中解析消息体。
     2. 根据消息体的内容，发送邮件给目标的邮箱。
     3. 手动应答 ACK，让消息队列删除该消息。
     */
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        System.out.println(message.getMessageProperties().getConsumerQueue());
        try {
            // 解析RabbitMQ消息体
            String messageBody = new String(message.getBody());
            MailMessageModel mailMessageModel = JSONObject.toJavaObject(JSONObject.parseObject(messageBody), MailMessageModel.class);
            String to = mailMessageModel.getTo();
            String subject = mailMessageModel.getSubject();
            String text = mailMessageModel.getText();
            sendHtmlMail(to, subject, text);
            // 手动ACK
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendHtmlMail(String to, String subject, String text) throws Exception {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setFrom(mailUsername);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(text, true);
        mailSender.send(mimeMessage);
    }
}