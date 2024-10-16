package ch.heigvd.dai.commands;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.Callable;

import ch.heigvd.dai.BMP.BMPImage;
import ch.heigvd.dai.BMP.BMPReader;
import ch.heigvd.dai.BMP.BMPWriter;
import ch.heigvd.dai.features.AsciiEffect;
import ch.heigvd.dai.features.BlurEffect;
import ch.heigvd.dai.features.Effect;
import ch.heigvd.dai.features.PepperEffect;
import picocli.CommandLine;

import ch.heigvd.dai.*;

@CommandLine.Command(name = "bmp_treatment", description = "Apply specified treatment to BMP file.")
public class Process implements Callable<Integer> {
    @CommandLine.ParentCommand protected Root parent;

    @Override
    public Integer call() {
        try {
            BMPImage image = new BMPImage();
            BMPReader reader = new BMPReader(parent.getInFilename(), image);
            reader.read();

            Effect effect = switch (parent.getTreatment()) {
                case GrayScale -> null;
                case PepperReduction -> new PepperEffect();
                case ImageToAscii -> new AsciiEffect();
                case Blur -> new BlurEffect();
            };

            effect.applyEffect(image);

            BMPWriter writer = new BMPWriter(parent.getOutFilename(), image);
            
            System.out.println(
                    "Applying treatment "
                            + parent.getTreatment()
                            + " to BMP file "
                            + parent.getInFilename()
                            + "and writing to "
                            + parent.getOutFilename()
                            + ".");
            return 0;
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
