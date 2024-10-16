package ch.heigvd.dai.features;

import ch.heigvd.dai.BMP.BMPImage;
import ch.heigvd.dai.BMP.BMPWriter;

import java.awt.*;
import java.io.BufferedOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
/*public class BlurEffect {

    static double[] kernel = {
            0.111, 0.111, 0.111,
            0.111, 0.111, 0.111,
            0.111, 0.111, 0.111
    }; // 3x3 kernel, sum = 1

    public void blurEffect(BMPImage image, String outFilename) {
        Color[] data = new Color[image.width * image.height];
        for (int y = 0; y < image.height; y++) {
            for (int x = 0; x < image.width; x++) {
                double r = 0;
                double g = 0;
                double b = 0;

                // Iterate through the 3x3 kernel
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        int pixelY = y + i;
                        int pixelX = x + j;

                        // Boundary check
                        if (pixelY >= 0 && pixelY < image.height && pixelX >= 0 && pixelX < image.width) {
                            Color pixel = image.byteData[pixelY * image.width + pixelX];
                            double kernelValue = kernel[(i + 1) * 3 + (j + 1)]; // Kernel index

                            // Accumulate the weighted color values
                            r += pixel.getRed() * kernelValue;
                            g += pixel.getGreen() * kernelValue;
                            b += pixel.getBlue() * kernelValue;
                        }
                    }
                }

                // Clamp the values to the valid range
                int finalR = (int)Math.min(255, Math.max(0, r));
                int finalG = (int)Math.min(255, Math.max(0, g));
                int finalB = (int)Math.min(255, Math.max(0, b));

                // Store the new blurred color
                data[y * image.width + x] = new Color(finalR, finalG, finalB);
            }
        }

        // Write the output image
        image.byteData = data;

        try (FileOutputStream fos = new FileOutputStream(outFilename);
             BufferedOutputStream bos = new BufferedOutputStream(fos)) {
            BMPWriter bmpw = new BMPWriter(bos, image);
            bmpw.write();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}*/

public class BlurEffect {

    static double[] kernel =
            {
                    0.111,0.111,0.111,
                    0.111,0.111,0.111,
                    0.111,0.111,0.111
            };

    public void blurEffect(BMPImage image,String outFilename){
        Color[] data = new Color[image.width * image.height];
        for(int y = 0; y < image.height; y++){
            for(int x = 0; x < image.width; x++){
                int start1 = (y == 0) ? 0 : -1;
                int start2 = (x == 0) ? 0 : -1;
                int end1 = (y == image.height-1) ? 0 : 1;
                int end2 = (x == image.width-1) ? 0 : 1;

                int r = 0;
                int g = 0;
                int b = 0;
                for(int i = start1; i <= end1; i++){
                    for(int j = start2; j <= end2; j++){
                        r += image.byteData[(y+i) * image.width + (x+j)].getRed() * kernel[(1+i) * 3 + (1+j)];
                        g += image.byteData[(y+i) * image.width + (x+j)].getGreen() * kernel[(1+i) * 3 + (1+j)];
                        b += image.byteData[(y+i) * image.width + (x+j)].getBlue() * kernel[(1+i) * 3 + (1+j)];
                    }
                }

                r = Math.min(255, Math.max(0, r));
                g = Math.min(255, Math.max(0, g));
                b = Math.min(255, Math.max(0, b));

                data[y * image.width + x] = new Color(r,g,b);
            }
        }
        image.byteData = data;

        try(FileOutputStream fos = new FileOutputStream(outFilename);
            BufferedOutputStream bos = new BufferedOutputStream(fos)){
            BMPWriter bmpw = new BMPWriter(bos,image);
            bmpw.write();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}
