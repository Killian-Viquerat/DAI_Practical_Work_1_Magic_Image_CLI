package ch.heigvd.dai.commands;

import picocli.CommandLine;

@CommandLine.Command(
        description = "A CLI application for the treatment of BMP images",
        version = "SNAPSHOT-1.0.0",
        subcommands = {
                Process.class,
        },
        scope = CommandLine.ScopeType.INHERIT,
        mixinStandardHelpOptions = true)

public class Root{
    public enum ImageTreatmentOptions {
        GrayScale,
        PepperReduction,
        ImageToAscii,
        Blur
    }

    @CommandLine.Parameters(index = "0", description = "The input image file name")
    protected String filename;

    @CommandLine.Parameters(index = "1", description = "The output file name")
    protected String output;

    @CommandLine.Option(
        names = {"-t", "--treatment"},
        description = "The treatment used on the BMP file (possible treatments being: ${COMPLETION-CANDIDATES}).",
        required = true
    )
    protected ImageTreatmentOptions treatment;

    @CommandLine.Option(
            names = {"-s", "--size"},
            description = "The size of the kernel used to average pixel together for the ascii effect (must be an odd number).",
            required = false
    )

    protected int size = 9;

    @CommandLine.Option(
            names = {"-c", "--color"},
            description = "Precise the output for the ascii file(used = html file with colored char | unused = txt file with black and white char )",
            required = false
    )

    protected boolean color = false;


    public int getSize() { return size; }
    public boolean getColor() { return color; }
    public String getInFilename() {return filename;}
    public String getOutFilename()  {return output;}

    public ImageTreatmentOptions getTreatment() {
        return treatment;
    }
}
