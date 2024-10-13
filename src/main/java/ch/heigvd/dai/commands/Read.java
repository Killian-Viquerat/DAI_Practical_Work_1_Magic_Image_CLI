package ch.heigvd.dai.commands;

import java.util.concurrent.Callable;
import picocli.CommandLine;

@CommandLine.Command(name = "bmp_treatment", description = "Apply specified treatment to BMP file.")
public class Read implements Callable<Integer> {
    @CommandLine.ParentCommand protected Root parent;

    @Override
    public Integer call() {

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
                    + parent.getFilename()
                    + ".");
        return 0;
    }
}
