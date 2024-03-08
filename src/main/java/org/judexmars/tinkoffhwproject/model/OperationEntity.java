package org.judexmars.tinkoffhwproject.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class OperationEntity {

    @Id
    private String id;

    @Column(name = "content")
    private String content;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Column(name = "type")
    private OperationType type;

    public enum OperationType {
        WRITE, READ
    }
}
