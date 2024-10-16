package ch.heigvd.dai.commands;

import picocli.CommandLine;

@CommandLine.Command(
        description = "A CLI application for the treatment of BMP images",
        version = "SNAPSHOT-1.0.0",
        subcommands = {
                Read.class,
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

    public String getInFilename() {return filename;}
    public String getOutFilename()  {return output;}

    public ImageTreatmentOptions getTreatment() {
        return treatment;
    }
}
