package org.judexmars.tinkoffhwproject.dto.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.List;

public record SendMessageDto(
        @NotBlank
        String author,
        @NotBlank
        String content,
        LocalDateTime lastModifiedDate,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        List<Long> imageIds
) {

    public SendMessageDto {
        lastModifiedDate = LocalDateTime.now();
    }
}
