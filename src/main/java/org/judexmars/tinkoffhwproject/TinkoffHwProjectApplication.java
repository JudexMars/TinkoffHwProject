package org.judexmars.tinkoffhwproject;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.judexmars.tinkoffhwproject.config.security.JwtProperties;
import org.judexmars.tinkoffhwproject.model.MessageEntity;
import org.judexmars.tinkoffhwproject.repository.MessageRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
@EnableCaching
@EnableConfigurationProperties(JwtProperties.class)
@SecurityScheme(name = "Auth",
                scheme = "bearer",
                type = SecuritySchemeType.HTTP,
                in = SecuritySchemeIn.HEADER,
                bearerFormat = "JWT")
public class TinkoffHwProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(TinkoffHwProjectApplication.class, args);
    }

    @Bean
    ApplicationRunner applicationRunner(MessageRepository messageRepository) {
        return args -> {
            messageRepository.save(MessageEntity.builder()
                    .author("JudexMars").content("Default message 1")
                    .build());
            messageRepository.save(MessageEntity.builder()
                    .author("Johnny Guitar").content("Default message 2")
                    .build());
        };
    }
}
