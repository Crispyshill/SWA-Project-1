package jdrs;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;

@Service
public class KafkaMessageListener {

	private final KafkaTemplate<String, Object> kafkaTemplate;


    KafkaMessageListener(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

 
    @KafkaListener(topics = "${app.topic.greetingtopic}")
    public void receive(@Payload Object person, @Headers MessageHeaders headers) {
        System.out.println("Received message: " + person.toString());
        person = processPerson(person);
        sendToModifiedTopic(person);
    }

    private Object processPerson(Object person) {
        return person;
 
    }

    private void sendToModifiedTopic(Object person) {
        MessageBuilder<Object> messageBuilder = MessageBuilder.withPayload(person);
        kafkaTemplate.send("modifiedtopic-out", messageBuilder.build());
    }
}
