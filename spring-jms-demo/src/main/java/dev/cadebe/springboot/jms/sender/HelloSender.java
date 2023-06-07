package dev.cadebe.springboot.jms.sender;

import dev.cadebe.springboot.jms.config.JmsConfig;
import dev.cadebe.springboot.jms.model.HelloWorldMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class HelloSender {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedRate = 2000)
    public void sendMessage() {
        HelloWorldMessage message = HelloWorldMessage.builder()
                .id(UUID.randomUUID())
                .message("Hello world!")
                .build();

        jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE, message);
    }

    @Scheduled(fixedRate = 2000)
    public void sendAndReceiveMessage() throws JMSException {
        System.out.println("I'm sending a message");

        HelloWorldMessage message = HelloWorldMessage.builder()
                .id(UUID.randomUUID())
                .message("Hello!")
                .build();

        Message receivedMsg = jmsTemplate.sendAndReceive(JmsConfig.MY_SEND_RECEIVE_QUEUE, session -> {
            try {
                Message helloMessage = session.createTextMessage(objectMapper.writeValueAsString(message));
                helloMessage.setStringProperty("_type", "dev.cadebe.springboot.jms.model.HelloWorldMessage");

                System.out.println("Sending hello");
                return helloMessage;

            } catch (JsonProcessingException e) {
                throw new JMSException("Boom");
            }
        });

        System.out.println("Message sent");
    }
}
