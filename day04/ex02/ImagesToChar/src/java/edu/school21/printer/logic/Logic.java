package edu.school21.printer.logic;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Logic {
    private final Ansi.BColor whiteChar;
    private final Ansi.BColor blackChar;
    private final ColoredPrinter coloredPrinter;

    public Logic(String blackChar, String whiteChar){
        this.whiteChar = Ansi.BColor.valueOf(whiteChar);
        this.blackChar = Ansi.BColor.valueOf(blackChar);
        coloredPrinter = new ColoredPrinter.Builder(1, false).foreground(Ansi.FColor.BLACK).build();
    }

    public void printImage(BufferedImage imagePath){
        for (int x = 0; x < imagePath.getWidth(); x++){
            for (int y = 0; y < imagePath.getHeight(); y++){
                Color color = new Color(imagePath.getRGB(y, x));
                if (color.getBlue() == 255 && color.getRed() == 255 && color.getGreen() == 255){
                    coloredPrinter.setBackgroundColor(blackChar);
                    coloredPrinter.print("  ");
                }
                if (color.getBlue() == 0 && color.getRed() == 0 && color.getGreen() == 0){
                    coloredPrinter.setBackgroundColor(whiteChar);
                    coloredPrinter.print("  ");
                }
            }
            System.out.print("\n");
        }
    }
}
