package com.pluralsight.bookstore.util;

public class TextUtil {
    public String sanitize(String textToSanitize){
        return textToSanitize.replaceAll("\\s+", " ");
    }
}

