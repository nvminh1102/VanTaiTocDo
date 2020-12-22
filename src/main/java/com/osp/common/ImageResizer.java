/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osp.common;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;

/**
 *
 * @author Lichvv
 */
public class ImageResizer {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    /**
     * Resizes an image to a absolute width and height (the image may not be
     * proportional)
     *
     * @param inputImagePath Path of the original image
     * @param outputImagePath Path to save the resized image
     * @param scaledWidth absolute width in pixels
     * @param scaledHeight absolute height in pixels
     * @throws IOException
     */
    public static void resize(File inputFile,
            String outputImagePath, int percent, long size)
            throws IOException {
        // reads input image
//        File inputFile = new File(inputImagePath);

        BufferedImage inputImage = ImageIO.read(inputFile);

        int scaledWidth = (int) (inputImage.getWidth() * percent);
        int scaledHeight = (int) (inputImage.getHeight() * percent);
        // creates output image
        BufferedImage outputImage = new BufferedImage(scaledWidth,
                scaledHeight,BufferedImage.TYPE_INT_RGB);

//        if (size > (500 * 1024)) {
            // scales the input image to the output image
            Graphics g2d = outputImage.createGraphics();
            g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
            g2d.setFont(g2d.getFont().deriveFont(25f));
            g2d.drawString(dateFormat.format(new Date()), 10, inputImage.getHeight() - 15);
            g2d.dispose();
            // extracts extension of output file
            String formatName = outputImagePath.substring(outputImagePath
                    .lastIndexOf(".") + 1);
            // writes to output file
            ImageIO.write(outputImage, formatName, new File(outputImagePath));

//        } else {
//            // scales the input image to the output image
//            Graphics g2d = inputImage.getGraphics();
//            g2d.setFont(g2d.getFont().deriveFont(30f));
//            g2d.drawString(dateFormat.format(new Date()), 20, inputImage.getHeight() - 30);
//            g2d.dispose();
//            // extracts extension of output file
//            String formatName = outputImagePath.substring(outputImagePath
//                    .lastIndexOf(".") + 1);
//            // writes to output file
//            ImageIO.write(inputImage, formatName, new File(outputImagePath));
//
//        }

    }

    /**
     * Test resizing images
     */
    public static void main(String[] args) {
        String inputImagePath = "D:/Photo/Puppy.jpg";
        String outputImagePath1 = "D:/Photo/Puppy_Fixed.jpg";
        String outputImagePath2 = "D:/Photo/Puppy_Smaller.jpg";
        String outputImagePath3 = "D:/Photo/Puppy_Bigger.jpg";
        String outputImagePath4 = "D:/Photo/Puppy_Bigger2.jpg";

//        try {
//            // resize to a fixed width (not proportional)
//            int scaledWidth = 1024;
//            int scaledHeight = 768;
//            ImageResizer.resize(inputImagePath, outputImagePath1, scaledWidth, scaledHeight);
// 
//            // resize smaller by 50%
//            double percent = 0.5;
//            ImageResizer.resize(inputImagePath, outputImagePath2, percent);
// 
//            // resize bigger by 50%
//            percent = 1.5;
//            ImageResizer.resize(inputImagePath, outputImagePath3, percent);
//            // resize bigger by 50%
//            percent = 1;
//            ImageResizer.resize(inputImagePath, outputImagePath4, percent);
// 
//        } catch (IOException ex) {
//            System.out.println("Error resizing the image.");
//            ex.printStackTrace();
//        }
    }

}
