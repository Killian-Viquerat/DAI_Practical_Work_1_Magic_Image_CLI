package ch.heigvd.dai.commands;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Callable;

import ch.heigvd.dai.BMP.*;
import ch.heigvd.dai.features.*;
import picocli.CommandLine;

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
            } else {
                try(FileWriter fw = new FileWriter(parent.getOutFilename(), StandardCharsets.UTF_8);
                    BufferedWriter bw = new BufferedWriter(fw)){
                    for(int y = ((AsciiEffect) effect).data.length - 1; y >= 0; y--){
                        for(int x = 0; x < ((AsciiEffect) effect).data[0].length - 1; x++){
                            bw.write(((AsciiEffect) effect).data[y][x]);
                        }
                        bw.write("\n");
                    }
                    bw.flush();
                }catch (IOException e){
                    System.out.println("Error writing to file" + e.getMessage());
                }
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
