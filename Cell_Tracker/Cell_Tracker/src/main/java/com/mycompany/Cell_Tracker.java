package com.company;
import  java.io.*;
import ij.*;
import ij.process.*;
import ij.gui.*;
import java.awt.*;
import ij.plugin.*;

public class Cell_Tracker {

    public static void main(String[] args) {
        // write your code here
        System.out.println("Hello Champ!");
        File dir = new File("/home/sinadabiri/Dropbox/Images/robo6_2x2_103pixelOverlap");
        String [] images = dir.list();

        if (images == null) { // if folder empty, give warning.
            System.out.println("Please make sure the directory exists and there are images inside.");
        } else {
            for (int i = 0; i < images.length; i++) { // loop through the image folder
                System.out.println(images[i]);
                if(images[i] ==".tif"){
                    System.out.println(images[i].split("_")[2]);//need to look up Java split function
                    ImagePlus imp = IJ.openImage(images[i]);
                    IJ.run(imp, "8-bit", "");
                    Prefs.blackBackground = true;
                    IJ.run(imp, "Make Binary", "");
                    IJ.run(imp, "Analyze Particles...", "  circularity=0.00-0.50 show=Masks display clear");
                    String Save_Path =
                            "/home/sinadabiri/Dropbox/Images/robo6_2x2_103pixelOverlap/PlugIn_Practice_output/" +
                                    "Mask_of_"+ images[i];
                    IJ.saveAs(imp, "Tiff", Save_Path);
                }

            }
        }

    }
}