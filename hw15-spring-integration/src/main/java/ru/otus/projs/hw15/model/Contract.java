package ru.otus.projs.hw15.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contract {
    private Long id;
    private String series;
    private String number;
    private Customer owner;
    private Document attachedDocument;
    private Rule rule;
}
