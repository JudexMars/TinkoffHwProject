package org.judexmars.tinkoffhwproject.dto;

import java.io.Serializable;

public record ImageDto (
    String name,
    Long size,
    String fileId
) implements Serializable {
}
