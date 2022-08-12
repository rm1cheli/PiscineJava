package edu.school21.printer.app;

import edu.school21.printer.logic.Logic;

import java.awt.image.BufferedImage;

public class Program {
    private static char WhitePixel;
    private static char BlackPixel;
    private static BufferedImage image;
    public static void main(String[] args) {
        if (args.length != 3){
            System.err.println("Error argument");
            System.exit(-1);
        }
        WhitePixel = args[0].charAt(0);
        BlackPixel = args[1].charAt(0);
        Logic logic = new Logic(BlackPixel, WhitePixel, args[2]);
        logic.printImage(BlackPixel, WhitePixel);
    }
}
