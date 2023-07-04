package convertimg.convert;

import convertimg.convertInter.TextColorSchema;
import convertimg.convertInter.TextGraphicsConverter;
import convertimg.sizeException.BadImageSizeException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.net.URL;

public class Converter implements TextGraphicsConverter {
    private double maxRatio = 0;
    private int height = -1;
    private int width = -1;
    private TextColorSchema schema = new ColorCharacter();

    @Override
    public String convert(String url) throws IOException, BadImageSizeException {


        BufferedImage img = ImageIO.read(new URL(url));
        int heightImg = img.getHeight();
        int widthImg = img.getWidth();

        if (maxRatio != 0) {
            int ratio;
            if (heightImg < widthImg) {
                ratio = widthImg / heightImg;
            } else {
                ratio = heightImg / widthImg;
            }
            if (ratio > maxRatio) {
                throw new BadImageSizeException(maxRatio, ratio);
            }
        }

        int newWidth;
        int newHeight;

        if (height < 0 && width < 0) {
            newHeight = heightImg;
            newWidth = widthImg;
        } else {
            if (width >= 0 && height >= 0) {
                if (width < widthImg || height < heightImg) {
                    if (((float) heightImg / height) > ((float) widthImg / width)) {
                        newWidth = (int) (widthImg / (float) (heightImg / height));
                        newHeight = (int) (heightImg / (float) (heightImg / height));
                    } else {
                        newWidth = (int) (widthImg / (float) (widthImg / width));
                        newHeight = (int) (heightImg / (float) (widthImg / width));
                    }
                } else {
                    newHeight = heightImg;
                    newWidth = widthImg;
                }
            } else if (width >= 0) {
                if (width < widthImg) {
                    newWidth = (int) (widthImg / (float) (widthImg / width));
                    newHeight = (int) (heightImg / (float) (widthImg / width));
                } else {
                    newHeight = heightImg;
                    newWidth = widthImg;
                }
            } else {
                if (height < heightImg) {
                    newWidth = (int) (widthImg / (float) (heightImg / height));
                    newHeight = (int) (heightImg / (float) (heightImg / height));
                } else {
                    newHeight = heightImg;
                    newWidth = widthImg;
                }
            }
        }

        Image scaledImage = img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);
        BufferedImage bwImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D graphics = bwImg.createGraphics();
        graphics.drawImage(scaledImage, 0, 0, null);
        WritableRaster bwRaster = bwImg.getRaster();

        String textImg = "";
        StringBuilder stringBuilder = new StringBuilder(textImg);
        int[] arrayCheck = new int[3];
        for (int h = 0; h < newHeight; h++) {
            for (int w = 0; w < newWidth; w++) {
                char c = schema.convert(bwRaster.getPixel(w, h, arrayCheck)[0]);
                stringBuilder.append(c + "" + c);
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public void setMaxWidth(int width) {
        this.width = width;
    }

    @Override
    public void setMaxHeight(int height) {
        this.height = height;
    }

    @Override
    public void setMaxRatio(double maxRatio) {
        this.maxRatio = maxRatio;
    }

    @Override
    public void setTextColorSchema(TextColorSchema schema) {
        this.schema = schema;
    }
}
