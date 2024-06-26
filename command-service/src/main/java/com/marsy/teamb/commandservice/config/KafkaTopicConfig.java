package com.marsy.teamb.commandservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic commandTopic(){
        return TopicBuilder.name("commandLog")
                .build();
    }

    @Bean
    public NewTopic missionStatusTopic(){
        return TopicBuilder.name("missionStatus")
                .build();
    }

    @Bean NewTopic anomaliesTriggers(){
        return TopicBuilder.name("MissionError")
                .build();
    }
}
