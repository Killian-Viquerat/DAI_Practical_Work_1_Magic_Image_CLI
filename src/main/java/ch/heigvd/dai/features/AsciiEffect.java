package ch.heigvd.dai.features;

import ch.heigvd.dai.BMP.BMPImage;

public class AsciiEffect implements Effect{

    static private char[] character = {' ','.',':','-','=','+','*','#','%','@'};
    private int size;
    public char[][] data;

    public AsciiEffect(int size){
        this.size = size;
    }

    public void applyEffect(BMPImage image){
        //Turn the image from color to grayScale
        GrayScaleEffect gray = new GrayScaleEffect();
        gray.applyEffect(image);

        //Find the new size of the image with integer division we loose some pixel in the process...
        int newWidth = image.width / size;
        int newHeight = image.height / size;

        data = new char[newHeight][newWidth];

        for(int y = 0 ; y < newHeight; ++y){
            for(int x = 0 ; x < newWidth; ++x){
                //Go through the kernel of size * size to make an average of the pixel intensity and downgrade the image quality
                double average = 0;
                for(int i = -(size / 2) ; i <= (size / 2); ++i){
                    for (int j = -(size / 2) ; j <= (size / 2); ++j){
                        average +=image.data[(y * size + size/2 + j) * image.width + (x * size + size/2 + i)].getBlue();
                    }
                }
                //To really make an accurately average we divide the result by the number of pixel
                average /= (size * size);
                //Then the average is used to map the value of the luminosity to the index of a corresponding ASCII character representing it
                data[y][x] = character[(int) (average / 255. * (double)character.length)];
            }
        }
    }
}
