package ch.heigvd.dai.features;

import ch.heigvd.dai.BMP.BMPImage;

public class AsciiEffect implements Effect{

    static private String[] character = {" ","'",".",":","-","=","+","*","#","%","@"};
    private int size;
    private boolean color;
    public String[][] data;

    public AsciiEffect(int size,boolean color){
        this.size = size;
        this.color = color;
    }

    public void applyEffect(BMPImage image){
        //Turn the image from color to grayScale
        if(!color) {
            GrayScaleEffect gray = new GrayScaleEffect();
            gray.applyEffect(image);
        }
        //Find the new size of the image with integer division we loose some pixel in the process...
        int newWidth = image.width / size;
        int newHeight = image.height / size;

        data = new String[newHeight][newWidth];

        for(int y = 0 ; y < newHeight; ++y){
            for(int x = 0 ; x < newWidth; ++x){
                //Go through the kernel of size * size to make an average of the pixel intensity and downgrade the image quality
                double averageR = 0;
                double averageG = 0;
                double averageB = 0;
                for(int i = -(size / 2) ; i <= (size / 2); ++i){
                    for (int j = -(size / 2) ; j <= (size / 2); ++j){
                        averageB +=image.data[(y * size + size/2 + j) * image.width + (x * size + size/2 + i)].getBlue();
                        averageG +=image.data[(y * size + size/2 + j) * image.width + (x * size + size/2 + i)].getGreen();
                        averageR +=image.data[(y * size + size/2 + j) * image.width + (x * size + size/2 + i)].getRed();
                    }
                }
                //To really make an accurately average we divide the result by the number of pixel
                averageR /= (size * size);
                averageG /= (size * size);
                averageB /= (size * size);
                //Then the average is used to map the value of the luminosity to the index of a corresponding ASCII character representing it
                if(color){
                    int index = (int) ((averageR+averageG+averageB)/3 / 255. * (double)character.length);
                    data[y][x] = "<div style=\"width: 10px; display: inline-block; color: rgb("+averageR+","+averageG+","+averageB+")\">" + (index == 0 ? "&nbsp;" : character[index]) +"</div>";
                }else{
                    data[y][x] = character[(int) (averageR / 255. * (double)character.length)];
                }
            }
        }
    }
}
