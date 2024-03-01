package org.judexmars.tinkoffhwproject.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

public record SendMessageDto(
        String author,
        String content,
        LocalDateTime lastModifiedDate,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        List<Long> imageIds
) {

    public SendMessageDto {
        lastModifiedDate = LocalDateTime.now();
    }
}
