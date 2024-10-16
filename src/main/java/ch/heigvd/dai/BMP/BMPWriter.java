package ch.heigvd.dai.BMP;

import java.io.*;

public class BMPWriter {

    BufferedOutputStream bos;
    BMPImage image;
    private int curPos = 0;

    public BMPWriter(String filename, BMPImage image){
        try (FileOutputStream fos = new FileOutputStream(filename);
             BufferedOutputStream bos = new BufferedOutputStream(fos)) {
            this.bos = bos;
            this.image = image;
        }catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void writeShort(short data) throws IOException {
        bos.write(data & 0xFF) ;
        bos.write((data >> 8) & 0xFF) ;
        curPos += 2;
    }

    private void writeInt(int data) throws IOException {
        bos.write(data & 0xFF) ;
        bos.write((data >> 8) & 0xFF) ;
        bos.write((data >> 16) & 0xFF) ;
        bos.write((data >> 24) & 0xFF) ;
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
        for(int i = 0; i < skip; i++){bos.write(0);}
        int nbPixel = image.height * image.width;
        for(int i = 0; i < nbPixel; i++) {
            bos.write(image.byteData[i].getBlue());
            bos.write(image.byteData[i].getGreen());
            bos.write(image.byteData[i].getRed());
        }
    }

    public void write()throws IOException{
        writeHeader();
        writeData();
    }
}
