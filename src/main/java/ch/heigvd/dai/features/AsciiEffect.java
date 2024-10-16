package ch.heigvd.dai.features;

import ch.heigvd.dai.BMP.BMPImage;

import java.awt.*;
import java.io.FileWriter;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class AsciiEffect implements Effect{

    char[] character = {' ','.',':','-','=','+','*','#','%','@'};
    int size = 41;

    public void applyEffect(BMPImage image){
        int imgSize = image.byteData.length;
        Color[] treated = new Color[imgSize];
        int average;
        for (int i = 0; i < imgSize; i++) {
            average = (image.byteData[i].getRed() + image.byteData[i].getGreen() + image.byteData[i].getBlue()) / 3;
            treated[i] = new Color(average, average, average);
        }
        image.byteData = treated;

        int newWidth = image.width / size;
        int newHeight = image.height / size;
        char[][] data = new char[newHeight][newWidth];
        for(int y = 0 ; y < newHeight; ++y){
            for(int x = 0 ; x < newWidth; ++x){
                double average2 = 0;
                for(int i = -(size / 2) ; i <= (size / 2); ++i){
                    for (int j = -(size / 2) ; j <= (size / 2); ++j){
                        average2 +=image.byteData[(y * size + size/2 + j) * image.width + (x * size + size/2 + i)].getBlue();
                    }
                }
                average2 /= (size * size);
                data[y][x] = character[(int) (average2 / 255. * (double)character.length)];
            }
        }

        try (FileWriter writer = new FileWriter("output.txt", StandardCharsets.UTF_8);){
            for (int y = data.length - 1; y >= 0; --y) {
                for (int x = 0; x < data[0].length; x++) {
                    writer.write(data[y][x]);
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
