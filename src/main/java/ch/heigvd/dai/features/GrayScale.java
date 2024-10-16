package ch.heigvd.dai.features;

import java.awt.*;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import ch.heigvd.dai.BMP.*;

public class GrayScale {
    void grayScale(BMPImage image, String outFilename) {
        int imgSize = image.byteData.length;
        Color[] treated = new Color[imgSize];
        int average;
        for (int i = 0; i < imgSize; i++) {
            average = (image.byteData[i].getRed() + image.byteData[i].getGreen() + image.byteData[i].getBlue()) / 3;
            treated[i] = new Color(average, average, average);
        }
        image.byteData = treated;

        try (FileOutputStream fos = new FileOutputStream(outFilename);
             BufferedOutputStream bos = new BufferedOutputStream(fos))
        {
            BMPWriter writer = new BMPWriter(bos, image);
            writer.write();
        }
        catch (IOException e) {
            System.err.println("IO Error: " + e);
        }
    }
}
