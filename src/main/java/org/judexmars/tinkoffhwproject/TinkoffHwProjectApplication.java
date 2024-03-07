package org.judexmars.tinkoffhwproject;

import org.judexmars.tinkoffhwproject.model.Message;
import org.judexmars.tinkoffhwproject.repository.MessageRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TinkoffHwProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(TinkoffHwProjectApplication.class, args);
    }

    @Bean
    ApplicationRunner applicationRunner(MessageRepository messageRepository) {
        return args -> {
            messageRepository.save(Message.builder()
                    .author("JudexMars").content("Default message 1")
                    .build());
            messageRepository.save(Message.builder()
                    .author("Johnny Guitar").content("Default message 2")
                    .build());
        };
    }
}
