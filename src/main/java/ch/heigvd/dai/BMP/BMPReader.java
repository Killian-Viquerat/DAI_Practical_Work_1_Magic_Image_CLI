package ch.heigvd.dai.BMP;

import java.io.*;
import java.awt.*;
//Source https://imagej.net/ij/source/ij/plugin/BMP_Reader.java

public class BMPReader {

    private BufferedInputStream bis;
    private BMPImage image;
    private int curPos = 0;

    public BMPReader(String filename, BMPImage image) {
        try (FileInputStream fis = new FileInputStream(filename);
             BufferedInputStream bis = new BufferedInputStream(fis)) {
            this.bis = bis;
            this.image = image;
        }catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public BMPImage read() throws Exception{
        getBMPHeader();
        if (image.magic != 0x4d42)
            throw new Exception("Not a BMP file");  // wrong file type
        if (image.compression!=0)
            throw new Exception("Compression not supported");
        getPixelData();
        return image;
    }

    private void getBMPHeader() throws Exception{
        image.magic = readShort();
        image.fileSize = readInt();
        image.reserved1 = readShort();
        image.reserved2 = readShort();
        image.bitmapOffset = readInt();
        image.headerSize = readInt();
        image.width = readInt();
        image.height = readInt();
        image.planes = readShort();
        image.bitsPerPixel = readShort();
        image.compression = readInt();
        image.actualSizeOfBitmap = readInt();
        image.xResolution = readInt();
        image.yResolution = readInt();
        image.actualColorsUsed = readInt();
        image.importantColors = readInt();

        if (image.height < 0) image.height = -image.height;
    }

    private short readShort() throws IOException {
        int b1 = bis.read();
        int b2 = bis.read();
        curPos += 2;
        return (short)((b2 << 8) + b1);
    }

    private int readInt() throws IOException {
        int b1 = bis.read();
        int b2 = bis.read();
        int b3 = bis.read();
        int b4 = bis.read();
        curPos += 4;
        return ((b4 << 24) + (b3 << 16) + (b2 << 8) + b1);
    }

    void getPixelData() throws  Exception {
        int skip = image.bitmapOffset - curPos;
        if(skip > 0) bis.skip(skip);
        int nbPixel = image.height * image.width;
        image.byteData = new Color[nbPixel];
        for(int i = 0; i < nbPixel; i++) {
            int b = bis.read();
            int g = bis.read();
            int r = bis.read();
            image.byteData[i] = new Color(r,g,b);
        }
    }

}
