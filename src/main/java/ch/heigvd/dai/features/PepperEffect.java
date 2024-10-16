package ch.heigvd.dai.features;

import ch.heigvd.dai.BMP.BMPImage;

import java.awt.*;
import java.util.Arrays;

public class PepperEffect implements Effect{
    public void applyEffect(BMPImage image) {
        // Applies a gray-scale effect to the image to make sure it is gray-scaled (pepper removal on colored
        // images is a very tedious task).
        GrayScaleEffect gs = new GrayScaleEffect();
        gs.applyEffect(image);

        Color[] treated = new Color[image.data.length];

        // A small kernel of the neighbourhood of a pixel, including itself.
        // Note that all values are set to 255, it's for the sorting of the data.
        int[] pixelsNeighbourhood = {255, 255, 255, 255, 255, 255, 255, 255, 255};

        for(int y = 0; y < image.height; y++){
            for(int x = 0; x < image.width; x++) {
                // Determines the starting and end position of the neighbourhood, taking into account the
                // borders of the image.
                int startYPos = (y == 0) ? 0 : -1;
                int startXPos = (x == 0) ? 0 : -1;
                int endYPos = (y == image.height - 1) ? 0 : 1;
                int endXPos = (x == image.width - 1) ? 0 : 1;

                int index = 0;
                // Reads the value from the neighbours and stores it inside the pixelsNeighbourhood tab, increasing
                // the number of pixels in the neighbourhood to easily know which value to take.
                for (int i = startYPos; i <= endYPos; i++) {
                    for (int j = startXPos; j <= endXPos; j++) {
                        pixelsNeighbourhood[index++] = image.data[(y + i) * image.width + (x + j)].getRed();
                    }
                }
                Arrays.sort(pixelsNeighbourhood);
                int middle = index % 2 == 0 ? index / 2 : index / 2 + 1;

                treated[y * image.width + x] = new Color(pixelsNeighbourhood[middle],
                        pixelsNeighbourhood[middle],
                        pixelsNeighbourhood[middle]);

                // Sets all values inside the pixelNeighbourhood to 255 for the future sorting
                for (int i = 0; i < 9; ++i)
                    pixelsNeighbourhood[i] = 255;
            }
        }
        image.data = treated;
    }
}
