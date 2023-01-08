package ru.otus.projs.hw15.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DocumentInfo {
    private String ownerName;
    private String ownerLastName;
    private String type;
}
