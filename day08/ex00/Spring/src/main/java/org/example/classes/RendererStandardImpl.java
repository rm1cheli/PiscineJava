package org.example.classes;

import org.example.interfaces.PreProcessor;
import org.example.interfaces.Renderer;

public class RendererStandardImpl implements Renderer {

    private PreProcessor pp;

    public RendererStandardImpl(PreProcessor pp) {
        this.pp = pp;
    }

    @Override
    public void renderer(String str) {
        System.out.println(pp.preProcessor(str));
    }
}