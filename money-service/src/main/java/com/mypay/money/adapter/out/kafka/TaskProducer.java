package com.mypay.money.adapter.out.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mypay.common.kafkatask.RechargingMoneyTask;
import com.mypay.money.application.port.out.SendRechargingMoneyTaskPort;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Slf4j
@Component
public class TaskProducer implements SendRechargingMoneyTaskPort {
    private final KafkaProducer<String, String> producer;
    private final String topic;

    public TaskProducer(@Value("${kafka.clusters.bootstrapservers}") String bootstrapServers,
                        @Value("${task.topic}")String topic) {

        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        this.producer = new KafkaProducer<>(props);
        this.topic = topic;
    }

    public void sendTask (String key, RechargingMoneyTask task) {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStringToProduce;
        try {
            jsonStringToProduce = objectMapper.writeValueAsString(task);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, jsonStringToProduce);
        producer.send(record, (metadata, exception) -> {
            if (exception == null) {
                log.info("Message sent successfully. Offset: " + metadata.offset());
            } else {
                exception.printStackTrace();
                log.error("Failed to send message: " + exception.getMessage());
            }
        });
    }

    @Override
    public void sendRechargingMoneyTaskPort(RechargingMoneyTask task) {
        this.sendTask(task.getTaskId(), task);
    }
}
