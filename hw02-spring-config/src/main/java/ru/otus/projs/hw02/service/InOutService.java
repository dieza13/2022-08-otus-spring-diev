package ru.otus.projs.hw02.service;

public interface InOutService {

    String readString();

    void writeString(String outString);
    void writeStringFromSource(String code);
    void writeStringFromSource(String code, String[] args);

}
