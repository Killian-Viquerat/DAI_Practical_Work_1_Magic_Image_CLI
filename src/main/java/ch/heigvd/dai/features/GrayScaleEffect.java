package ch.heigvd.dai.features;

import java.awt.*;

import ch.heigvd.dai.BMP.*;

public class GrayScaleEffect implements Effect {
    public void applyEffect(BMPImage image) {
        int imgSize = image.data.length;
        Color[] treated = new Color[imgSize];
        int average;
        // Traverses every pixel of the image and makes an average of the color by summing the r/g/b values
        // and dividing it by 3, and making a new color with this value.
        for (int i = 0; i < imgSize; i++) {
            average = (image.data[i].getRed() + image.data[i].getGreen() + image.data[i].getBlue()) / 3;
            treated[i] = new Color(average, average, average);
        }
        image.data = treated;
    }
}
