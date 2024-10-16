package ch.heigvd.dai.commands;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.Callable;

import ch.heigvd.dai.BMP.*;
import ch.heigvd.dai.features.*;
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

            int size = parent.getSize() > 0 && parent.getSize() % 2 == 1 ? parent.getSize() : 9;

            Effect effect = switch (parent.getTreatment()) {
                case GrayScale -> new GrayScaleEffect();
                case PepperReduction -> new PepperEffect();
                case ImageToAscii -> new AsciiEffect(size);
                case Blur -> new BlurEffect();
            };

            effect.applyEffect(image);

            if(!(parent.getTreatment() == Root.ImageTreatmentOptions.ImageToAscii)){
                BMPWriter writer = new BMPWriter(parent.getOutFilename(), image);
                writer.write();
            }

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
            return -1;
        }
    }
}
