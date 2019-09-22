package com.cadebe.persons_api.util;

public enum FileData {
    DELIMETER(","),
    FILE_NAME("src/main/resources/sourceData/mockData.csv");

    private final String text;

    private FileData(final String color) {
        this.text = color;
    }

    @Override
    public String toString() {
        return text;
    }
}
