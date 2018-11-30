/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */

import java.io.File;

import net.imagej.Dataset;
import net.imagej.ImageJ;
import ij.*;
//import ij.process.*;
//import ij.gui.*;
//import java.awt.*;
//import ij.plugin.*;

/** Loads and displays a dataset using the ImageJ API. */
public class ParticleAnalyzer {

    public static void main(final String... args) throws Exception {
//      create the ImageJ application context with all available services
		final ImageJ ij = new ImageJ();

		// ask the user for a file to open
//		final File file = ij.ui().chooseFile(null, "open");

//		// load the dataset
//		final Dataset dataset = ij.scifio().datasetIO().open(file.getPath());
//
//		// display the dataset
//		ij.ui().show(dataset);

        // my script

        System.out.println("Hello Champ!");
//        File dir = new File("/home/sinadabiri/Dropbox/Images/robo6_2x2_103pixelOverlap");
//        String [] images = dir.list();
//      ___________________________________________________________
        final File file = ij.ui().chooseFile(null, "open");
        final Dataset dataset = ij.scifio().datasetIO().open(file.getPath());

        ImagePlus imp = ij.ui().show(dataset);

//        ImagePlus imp = IJ.openImage(images);
        IJ.run(imp, "8-bit", "");
        Prefs.blackBackground = true;
        IJ.run(imp, "Make Binary", "");
        IJ.run(imp, "Analyze Particles...",
                "  circularity=0.00-0.50 show=Masks display clear");
        String Save_Path =
                "/home/sinadabiri/Dropbox/Images/robo6_2x2_103pixelOverlap/PlugIn_Practice_output/" +"Mask_of_"+ images;
        IJ.saveAs(imp, "Tiff", Save_Path);

//        ________________________________________________________________
//        if (images == null) { // if folder empty, give warning.
//            System.out.println("Please make sure the directory exists and there are images inside.");
//        } else {
//            for (int i = 0; i < images.length; i++) { // loop through the image folder
//                System.out.println(images[i]);
//                if(images[i] ==".tif"){
//                    System.out.println(images[i].split("_")[2]);//need to look up Java split function
//                    ImagePlus imp = IJ.openImage(images[i]);
//                    IJ.run(imp, "8-bit", "");
//                    Prefs.blackBackground = true;
//                    IJ.run(imp, "Make Binary", "");
//                    IJ.run(imp, "Analyze Particles...",
//                            "  circularity=0.00-0.50 show=Masks display clear");
//                    String Save_Path =
//                            "/home/sinadabiri/Dropbox/Images/robo6_2x2_103pixelOverlap/PlugIn_Practice_output/" +"Mask_of_"+ images[i];
//                    IJ.saveAs(imp, "Tiff", Save_Path);
//                }
//
//            }
//        }

    }

}