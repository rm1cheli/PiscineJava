package edu.school21.printer.app;

import edu.school21.printer.logic.Logic;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Parameters(separators = "=")
public class Program {
    private static final String IMAGE_PATH = "src/resources/it.bmp";

    @Parameter(names = {"--white"})
    private static String WHITE_PIXEL;
    @Parameter(names = {"--black"})
    private static String BLACK_PIXEL;
    private static BufferedImage image;

    public static void main(String[] args) {
        if (args.length != 2){
            System.out.println("Error argument");
            System.exit(-1);
        }
        Program main = new Program();
        JCommander.newBuilder()
                .addObject(main)
                .build()
                .parse(args);
        run();
    }

    private static void run() {
        try {
            image = ImageIO.read(new File(IMAGE_PATH));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        new Logic(WHITE_PIXEL, BLACK_PIXEL).printImage(image);
    }
}
