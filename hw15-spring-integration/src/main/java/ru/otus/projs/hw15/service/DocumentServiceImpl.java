package ru.otus.projs.hw15.service;

import org.springframework.stereotype.Service;
import ru.otus.projs.hw15.exception.DocumentNotFound;
import ru.otus.projs.hw15.model.Document;
import ru.otus.projs.hw15.model.DocumentType;

import java.util.HashMap;
import java.util.Map;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final Map<String, Document> documents = new HashMap<>() {{
        put("NAME1_LASTNAME1_ПАСПОРТ", new Document(1L, "ser1", "num1", 1L, DocumentType.passport));
        put("NAME1_LASTNAME1_ПРАВА", new Document(2L, "ser2", "num2", 1L, DocumentType.driver_license));
        put("NAME2_LASTNAME2_ПАСПОРТ", new Document(3L, "ser3", "num3", 2L, DocumentType.passport));
        put("NAME2_LASTNAME2_ПРАВА", new Document(4L, "ser4", "num4", 2L, DocumentType.driver_license));
        put("NAME3_LASTNAME3_ПАСПОРТ", new Document(5L, "ser5", "num5", 3L, DocumentType.passport));
        put("NAME3_LASTNAME3_ПРАВА", new Document(6L, "ser6", "num6", 3L, DocumentType.driver_license));
        put("NAME4_LASTNAME4_ПАСПОРТ", new Document(7L, "ser7", "num7", 4L, DocumentType.passport));
        put("NAME4_LASTNAME4_ПРАВА", new Document(8L, "ser8", "num8", 4L, DocumentType.driver_license));
        put("NAME5_LASTNAME5_ПАСПОРТ", new Document(9L, "ser9", "num9", 5L, DocumentType.passport));
        put("NAME5_LASTNAME5_ПРАВА", new Document(10L, "ser10", "num10", 5L, DocumentType.driver_license));

    }};

    @Override
    public Document getDocumentByOwnerAndType(String name, String lastName, String type) {
        String key = (name + "_" + lastName + "_" + type).toUpperCase();
        if (documents.containsKey(key)) {
            return documents.get(key);
        } else {
            throw  new DocumentNotFound();
        }
    }
}
