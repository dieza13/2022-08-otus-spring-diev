package ru.otus.projs.hw04.service;

public interface MessageWriterService {

    void writeStringFromSource(String code);
    void writeStringFromSource(String code, String ... args);

}
