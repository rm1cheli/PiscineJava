package edu.school21.printer.logic;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Logic {
    private final char whiteChar;
    private final char blackChar;
    private final String path;

    public Logic(char blackChar, char whiteChar, String path){
        this.path = path;
        this.whiteChar = whiteChar;
        this.blackChar = blackChar;
    }

    public void printImage(char blackChar, char whiteChar){
        try {
            File file = new File(path);
            BufferedImage sor = ImageIO.read(file);
            for (int x = 0; x < sor.getWidth(); x++){
                for (int y = 0; y < sor.getHeight(); y++){
                    Color color = new Color(sor.getRGB(y, x));
                    if (color.getBlue() == 255 && color.getRed() == 255 && color.getGreen() == 255){
                        System.out.print(whiteChar + " ");
                    }
                    if (color.getBlue() == 0 && color.getRed() == 0 && color.getGreen() == 0){
                        System.out.print(blackChar + " ");
                    }
                }
                System.out.print("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
