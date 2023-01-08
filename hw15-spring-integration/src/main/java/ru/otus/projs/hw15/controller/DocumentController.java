package ru.otus.projs.hw15.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.projs.hw15.model.Document;
import ru.otus.projs.hw15.model.dto.DocumentInfo;
import ru.otus.projs.hw15.service.DocumentService;

@RestController
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping(path = "/document")
    public Document getDocument(@RequestBody DocumentInfo documentInfo) {

        return documentService.getDocumentByOwnerAndType(documentInfo.getOwnerName(),documentInfo.getOwnerLastName(),
                documentInfo.getType());

    }

}
