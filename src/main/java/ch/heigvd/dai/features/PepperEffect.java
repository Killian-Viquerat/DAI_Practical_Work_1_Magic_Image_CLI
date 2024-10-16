package ch.heigvd.dai.features;

import ch.heigvd.dai.BMP.BMPImage;
import ch.heigvd.dai.BMP.BMPWriter;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.awt.*;
import java.util.Arrays;

public class PepperEffect implements Effect{
    public void applyEffect(BMPImage image) {
        GrayScale gs = new GrayScale();
        gs.grayScale(image, "output.bmp");

        Color[] treated = new Color[image.byteData.length];

        int[] pixelsNeighbourhood = new int[9];

        for(int y = 0; y < image.height; y++){
            for(int x = 0; x < image.width; x++) {
                int start1 = (y == 0) ? 0 : -1;
                int start2 = (x == 0) ? 0 : -1;
                int end1 = (y == image.height - 1) ? 0 : 1;
                int end2 = (x == image.width - 1) ? 0 : 1;

                int index = 0;

                for (int i = start1; i <= end1; i++) {
                    for (int j = start2; j <= end2; j++) {
                        pixelsNeighbourhood[index++] = image.byteData[(y+i) * image.width + (x+j)].getRed();
                    }
                }
                int middle = index % 2 == 0 ? index / 2 : index / 2 + 1;
                Arrays.sort(pixelsNeighbourhood);
                treated[y * image.width + x] = new Color(pixelsNeighbourhood[middle],
                        pixelsNeighbourhood[middle],
                        pixelsNeighbourhood[middle]);
            }
        }
        image.byteData = treated;

        try (FileOutputStream fos = new FileOutputStream("output.bmp");
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
