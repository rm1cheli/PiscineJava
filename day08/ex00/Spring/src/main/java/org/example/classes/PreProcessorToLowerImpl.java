package org.example.classes;

import org.example.interfaces.PreProcessor;

import java.util.Locale;

public class PreProcessorToLowerImpl implements PreProcessor {
    @Override
    public String preProcessor(String str) {
        String str1 = str.toLowerCase(Locale.ROOT);
        return str1;
    }
}