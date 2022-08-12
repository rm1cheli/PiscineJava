package org.example.classes;

import org.example.interfaces.PreProcessor;

public class PreProcessorToUpperImpl implements PreProcessor {
    @Override
    public String preProcessor(String str) {
        return str.toUpperCase();
    }
}