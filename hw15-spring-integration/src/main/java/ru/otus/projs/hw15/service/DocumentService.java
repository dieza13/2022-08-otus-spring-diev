package ru.otus.projs.hw15.service;

import ru.otus.projs.hw15.model.Document;

public interface DocumentService {
    Document getDocumentByOwnerAndType(String name, String lastName, String type);
}
