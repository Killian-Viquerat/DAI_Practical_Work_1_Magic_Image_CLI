package ch.heigvd.dai.BMP;

import java.io.*;

public class BMPWriter {

    BufferedOutputStream bos;
    BMPImage image;
    private int curPos = 0;

    public BMPWriter(String filename, BMPImage image){
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filename);
            bos = new BufferedOutputStream(fos);
            this.image = image;
        }catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    //Translate bigEndian into littleEndian for short
    private void writeShort(short data) throws IOException {
        bos.write(data & 0xFF) ;
        bos.write((data >> 8) & 0xFF) ;
        curPos += 2;
    }
    //Translate bigEndian into littleEndian for Int
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
            bos.write(image.data[i].getBlue());
            bos.write(image.data[i].getGreen());
            bos.write(image.data[i].getRed());
        }
    }

    public void write()throws IOException{
        writeHeader();
        writeData();
        bos.flush();
        bos.close();
    }
}
