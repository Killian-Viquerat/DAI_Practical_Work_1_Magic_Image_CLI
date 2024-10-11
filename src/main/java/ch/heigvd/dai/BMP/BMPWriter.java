package ch.heigvd.dai.BMP;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

public class BMPWriter {

    BufferedOutputStream os;
    BMPImage image;
    private int curPos = 0;

    public BMPWriter(BufferedOutputStream os, BMPImage image){
        this.os = os;
        this.image = image;
    }

    private void writeShort(short data) throws IOException {
        os.write(data & 0xFF) ;
        os.write((data >> 8) & 0xFF) ;
        curPos += 2;
    }

    private void writeInt(int data) throws IOException {
        os.write(data & 0xFF) ;
        os.write((data >> 8) & 0xFF) ;
        os.write((data >> 16) & 0xFF) ;
        os.write((data >> 24) & 0xFF) ;
        curPos += 4;
    }

    private void writeHeader() throws IOException{
        writeShort(image.magic);
        writeInt(image.fileSize);
        writeShort(image.reserved1);
        writeShort(image.reserved2);
        writeInt(image.bitmapOffset);
        writeInt(image.headerSize);
        writeInt(image.width);
        writeInt(image.height);
        writeShort(image.planes);
        writeShort(image.bitsPerPixel);
        writeInt(image.compression);
        writeInt(image.actualSizeOfBitmap);
        writeInt(image.xResolution);
        writeInt(image.yResolution);
        writeInt(image.actualColorsUsed);
        writeInt(image.importantColors);
    }

    private void writeData() throws IOException{
        int skip = image.bitmapOffset - curPos;
        for(int i = 0; i < skip; i++){os.write(0);}
        int nbPixel = image.height * image.width;
        for(int i = 0; i < nbPixel; i++) {
            os.write(image.byteData[i].getBlue());
            os.write(image.byteData[i].getGreen());
            os.write(image.byteData[i].getRed());
        }
    }

    public void write()throws IOException{
        writeHeader();
        writeData();
    }
}
