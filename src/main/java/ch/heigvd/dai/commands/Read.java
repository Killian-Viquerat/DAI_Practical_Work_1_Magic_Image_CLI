package ch.heigvd.dai.commands;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.Callable;

import ch.heigvd.dai.BMP.BMPImage;
import ch.heigvd.dai.BMP.BMPReader;
import picocli.CommandLine;

@CommandLine.Command(name = "bmp_treatment", description = "Apply specified treatment to BMP file.")
public class Read implements Callable<Integer> {
    @CommandLine.ParentCommand protected Root parent;

    @Override
    public Integer call() {
        try (FileInputStream fis = new FileInputStream(parent.getInFilename());
        BufferedInputStream bis = new BufferedInputStream(fis)){
            BMPImage image = new BMPImage();
            BMPReader reader = new BMPReader(bis, image);
//        switch (parent.getTreatment()){
//            case GrayScale -> ;
//            case PepperReduction -> ;
//            case ImageToAscii -> ;
//            case Blur -> ;
//        }
        /*

         Put function that needs to be called here

         */


            System.out.println(
                    "Applying treatment "
                            + parent.getTreatment()
                            + " to BMP file "
                            + parent.getInFilename()
                            + "and writing to "
                            + parent.getOutFilename()
                            + ".");
            return 0;
        } catch (IOException e){
            System.err.println("Error: " + e.getMessage());
            return -1;
        }

    }
}
