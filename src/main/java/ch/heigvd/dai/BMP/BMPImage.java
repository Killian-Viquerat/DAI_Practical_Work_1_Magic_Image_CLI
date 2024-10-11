package ch.heigvd.dai.BMP;

import java.awt.*;

public class BMPImage {
    public short magic; // Nombre MAGIC
    public int fileSize; // Taille totale du fichier
    public short reserved1; // Réservé (0)
    public short reserved2; // Réservé (0)
    public int bitmapOffset; // Offset des données de l'image
    public int headerSize; // Taille de l'en-tête du fichier
    public int width; // Largeur de l'image
    public int height;  // Hauteur de l'image
    public short planes; // Nombre de plans (toujours 1)
    public short bitsPerPixel; // Bits par pixel (24 bits dans notre cas)
    public int compression; // Méthode de compression (0 pour non compressé)
    public int actualSizeOfBitmap; // Taille de l'image (sans en-tête)
    public int xResolution; // Résolution horizontale (en pixels par mètre)
    public int yResolution; // Résolution verticale (en pixels par mètre)
    public int actualColorsUsed; // Nombre de couleurs utilisées dans l'image (0 pour toutes)
    public int importantColors; // Nombre de couleurs importantes (0 pour toutes)

    public Color[] byteData;
    boolean topDown;


    public BMPImage(){};
}