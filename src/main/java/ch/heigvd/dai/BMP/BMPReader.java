package ch.heigvd.dai.BMP;

import java.io.*;
import java.awt.*;
//Source https://imagej.net/ij/source/ij/plugin/BMP_Reader.java

public class BMPReader {

    private BufferedInputStream bis;
    private BMPImage image;
    private int curPos = 0;

    public BMPReader(String filename, BMPImage image) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(filename);
            this.bis = new BufferedInputStream(fis);;
            this.image = image;
        } catch (Exception e) {
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
        bis.close();
        return image;
    }

    private void getBMPHeader() throws IOException{
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
    //Translate littleEndian into bigEndian for short
    private short readShort() throws IOException {
        int b1 = bis.read();
        int b2 = bis.read();
        curPos += 2;
        return (short)((b2 << 8) + b1);
    }
    //Translate littleEndian into bigEndian for int
    private int readInt() throws IOException {
        int b1 = bis.read();
        int b2 = bis.read();
        int b3 = bis.read();
        int b4 = bis.read();
        curPos += 4;
        return ((b4 << 24) + (b3 << 16) + (b2 << 8) + b1);
    }

    void getPixelData() throws IOException {
        int skip = image.bitmapOffset - curPos;
        if(skip > 0) bis.skip(skip);
        int nbPixel = image.height * image.width;
        image.data = new Color[nbPixel];
        for(int i = 0; i < nbPixel; i++) {
            int b = bis.read();
            int g = bis.read();
            int r = bis.read();
            image.data[i] = new Color(r,g,b);
        }
    }

}
