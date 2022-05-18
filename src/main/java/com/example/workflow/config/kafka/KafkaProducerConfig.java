package com.example.workflow.config.kafka;

import com.example.workflow.dto.MailMessage;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {
    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;
    @Value(value = "${spring.kafka.producer.transaction-id-prefix}")
    private String trIdPref;

    @Bean
    public ProducerFactory<String, MailMessage> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapAddress);
        configProps.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        configProps.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class);
        DefaultKafkaProducerFactory<String, MailMessage> pf = new DefaultKafkaProducerFactory<>(configProps);
        pf.transactionCapable();
        pf.setTransactionIdPrefix(trIdPref);
        return pf;
    }

    @Bean
    public KafkaTemplate<String, MailMessage> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}