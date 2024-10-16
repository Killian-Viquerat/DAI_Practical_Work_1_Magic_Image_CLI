package ch.heigvd.dai.features;

import ch.heigvd.dai.BMP.BMPImage;
import ch.heigvd.dai.BMP.BMPWriter;

import java.awt.*;
import java.io.BufferedOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;

public class BlurEffect {

    static double[] kernel = {0.111,0.111,0.111,0.111,0.111,0.111,0.111,0.111,0.111};

    void blurEffect(BMPImage image,String outFilename){
        Color[] data = image.byteData.clone();
        for(int y = 0; y < image.height; y++){
            for(int x = 0; x < image.width; x++){
                int start1 = (y == 0) ? 0 : -1;
                int start2 = (x == 0) ? 0 : -1;
                int end1 = (y == image.height-1) ? image.height-1 : y+1;
                int end2 = (x == image.width-1) ? image.width-1 : x+1;

                int r = 0;
                int g = 0;
                int b = 0;
                for(int i = start1; i < end2; i++){
                    for(int j = start2; j < end2; j++){
                        r += image.byteData[(y+i) * image.width + (x+j)].getBlue() * kernel[(2+i) * image.width + (2+j)];
                        g += image.byteData[(y+i) * image.width + (x+j)].getGreen() * kernel[(2+i) * image.width + (2+j)];
                        b += image.byteData[(y+i) * image.width + (x+j)].getRed() * kernel[(2+i) * image.width + (2+j)];
                    }
                }
                data[y * image.width + x] = new Color(r,g,b);
            }
        }
        image.byteData = data.clone();

        try(FileOutputStream fos = new FileOutputStream(outFilename);
            BufferedOutputStream bos = new BufferedOutputStream(fos)){
            BMPWriter bmpw = new BMPWriter(bos,image);
            bmpw.write();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}
