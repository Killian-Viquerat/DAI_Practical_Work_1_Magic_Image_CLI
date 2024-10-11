package ch.heigvd.dai.BMP;

import java.io.*;
import java.awt.*;
//Source https://imagej.net/ij/source/ij/plugin/BMP_Reader.java

public class BMPReader {

    private BufferedInputStream is;
    private BMPImage image;
    private int curPos = 0;

    public BMPReader(BufferedInputStream is, BMPImage image) {
        this.is = is;
        this.image = image;
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
        int b1 = is.read();
        int b2 = is.read();
        curPos += 2;
        return (short)((b2 << 8) + b1);
    }

    private int readInt() throws IOException {
        int b1 = is.read();
        int b2 = is.read();
        int b3 = is.read();
        int b4 = is.read();
        curPos += 4;
        return ((b4 << 24) + (b3 << 16) + (b2 << 8) + b1);
    }

    void getPixelData() throws  Exception {
        int skip = image.bitmapOffset - curPos;
        if(skip > 0) is.skip(skip);
        int nbPixel = image.height * image.width;
        image.byteData = new Color[nbPixel];
        for(int i = 0; i < nbPixel; i++) {
            int b = is.read();
            int g = is.read();
            int r = is.read();
            image.byteData[i] = new Color(r,g,b);
        }
    }

}
