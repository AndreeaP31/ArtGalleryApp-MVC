package com.example.artgallery.model;

public enum SupportedLanguage {
    ENGLISH("en", "English"),
    FRENCH("fr", "Français"),
    GERMAN("de", "Deutsch"),
    ROMANIAN("ro", "Română");

    private final String code;
    private final String displayName;

    SupportedLanguage(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
