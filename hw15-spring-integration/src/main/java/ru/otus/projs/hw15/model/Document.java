package ru.otus.projs.hw15.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Document {
    private Long id;
    private String series;
    private String number;
    private Long customerId;
    private DocumentType documentType;
}
