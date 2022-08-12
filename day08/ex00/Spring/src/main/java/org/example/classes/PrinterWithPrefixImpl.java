package org.example.classes;

import org.example.interfaces.Printer;
import org.example.interfaces.Renderer;

public class PrinterWithPrefixImpl implements Printer {

    private Renderer renderer;
    private String prefix= "";

    public PrinterWithPrefixImpl(Renderer renderer) {
        this.renderer = renderer;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public void print(String str) {
        if(!prefix.isEmpty())
            renderer.renderer(prefix + str);
        else
            renderer.renderer(str);
    }
}