package ch.heigvd.dai.features;

import ch.heigvd.dai.BMP.BMPImage;

import java.awt.*;

public class BlurEffect implements Effect{

    // The kernel for a Box blur processing, thanks to Wikipedia: https://en.wikipedia.org/wiki/Kernel_(image_processing)
    static double[] kernel =
            {
                    1. / 9., 1. / 9., 1. / 9.,
                    1. / 9., 1. / 9., 1. / 9.,
                    1. / 9., 1. / 9., 1. / 9.
            };

    public void applyEffect(BMPImage image){
        Color[] data = new Color[image.width * image.height];
        //Traverse all the pixel in the picture
        for(int y = 0; y < image.height; y++){
            for(int x = 0; x < image.width; x++){
                //Those variables permit the detection of edge and prevent processing no existent pixels
                int start1 = (y == 0) ? 0 : -1;
                int start2 = (x == 0) ? 0 : -1;
                int end1 = (y == image.height-1) ? 0 : 1;
                int end2 = (x == image.width-1) ? 0 : 1;

                int r = 0;
                int g = 0;
                int b = 0;

                //Blur effect is made by Convolution of two matrices in our case a sample of 9X9 center on the current processed pixel and the kernel
                for(int i = start1; i <= end1; i++){
                    for(int j = start2; j <= end2; j++){
                        r += image.data[(y+i) * image.width + (x+j)].getRed() * kernel[(1+i) * 3 + (1+j)];
                        g += image.data[(y+i) * image.width + (x+j)].getGreen() * kernel[(1+i) * 3 + (1+j)];
                        b += image.data[(y+i) * image.width + (x+j)].getBlue() * kernel[(1+i) * 3 + (1+j)];
                    }
                }
                //Prevent the value to go above 255 or below 0
                r = Math.min(255, Math.max(0, r));
                g = Math.min(255, Math.max(0, g));
                b = Math.min(255, Math.max(0, b));

                data[y * image.width + x] = new Color(r,g,b);
            }
        }
        image.data = data;
    }
}
