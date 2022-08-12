package org.example.classes;

import org.example.interfaces.PreProcessor;
import org.example.interfaces.Renderer;

public class RendererErrImpl implements Renderer {

    private PreProcessor pp;

    RendererErrImpl(PreProcessor pp){
        this.pp = pp;
    }

    @Override
    public void renderer(String str) {
        System.err.println(pp.preProcessor(str));
    }
}
