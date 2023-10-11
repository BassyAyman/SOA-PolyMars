package com.marsy.teamb.rocketservice.config;

import com.marsy.teamb.rocketservice.modele.MarsyLog;
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

    @Value("${spring.kafka.bootstrap-servers}")
    private String boostrapServers;

    /**
     * methode of configuration that permit to send String to Kafka
     * @return rules over the server to send Strings
     */
    public Map<String, Object> producerConfig(){
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, boostrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return props;
    }

    /**
     * methode that is responsible to create producer that send a String
     * could be other thing as we mentioned object un producerConfig
     * in the methode Factory change the Value to the wanted object
     */
    @Bean
    public ProducerFactory<String, MarsyLog> producerFactory(){
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    /**
     * methode to send message through kafka
     */
    @Bean
    public KafkaTemplate<String, MarsyLog> kafkaTemplate(ProducerFactory<String, MarsyLog> producerFactory){
        return new KafkaTemplate<>(producerFactory);
    }
}
