package GUIcomponents; /**
 * Created by elliot on 10/14/15.
 */
import ij.IJ;

import ij.io.OpenDialog;
import ij.io.SaveDialog;
import org.micromanager.api.PositionList;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Map;

public class MakeCSV {

    SaveDialog sd;
    OpenDialog attach;

    public MakeCSV(Map<String,String>imgParams,Map<String,String>calendarDates,Map<String,String>time,PositionList wells,double duration,String plateID){
        sd = new SaveDialog("Save Template",imgParams.get("name"),".csv");
        attach = new OpenDialog("Attach A Plate Manifest");
        String path = sd.getDirectory();
        String filename = sd.getFileName();
        String barcode = getBarcode();
        File F = new File(path+filename);
        PrintWriter pw;
        try {
            pw = new PrintWriter(F);
            if (!imgParams.get("microscope").equals("Robo3")) {
                pw.append("Experiment Name,Barcode,Stack,Shelf,Author,Description,Plate,Well count,Microscope,Image Folder,Incubator,PFSPerTile\n");
            } else if (imgParams.get("microscope").equals("Robo3")) {
                pw.append("Experiment Name,Barcode,Stack,Shelf,Author,Description,Plate,Objective,Camera,Overlap\n");
            }

            //pw.append(imgParams.get("name")+",");
            pw.append(filename.replace(".csv", "").replace("_", "-") + ",");
            //pw.append(imgParams.get("barcode")+",");
            pw.append(getBarcode() + ",");
            pw.append(imgParams.get("stack") + ",");
            //IJ.log("Stack and shelf are: "+imgParams.get("stack")+", "+imgParams.get("shelf"));
            pw.append(imgParams.get("shelf") + ","); // leave Stack blank for manual entry on loading
            pw.append(imgParams.get("author") + ",");
            pw.append(imgParams.get("name") + ","); // name == description
            pw.append(imgParams.get("plate") + ","); //CCB or TPP

            if (!(imgParams.get("microscope").equals("Robo3"))) {
                //pw.append(imgParams.get("wellCount") + ",");
                pw.append(plateID + ",");
                pw.append(imgParams.get("microscope") + ",");
                if (imgParams.get("microscope").equals("Robo4")) pw.append("E:/Images" + ",");
                if (imgParams.get("microscope").equals("Robo5")) pw.append("E:/Images" + ",");
                if (imgParams.get("microscope").equals("Robo6")) pw.append("C:/Images" + ",");
                pw.append("2,"); // for the second incubator
                pw.append(imgParams.get("pfsPerTile"));
            } else if (imgParams.get("microscope").equals("Robo3")) {
                pw.append(imgParams.get("obj") + ",");
                pw.append(imgParams.get("camera") + ","); // will need to change this when the sCMOS is attached to the microscope
                pw.append(imgParams.get("overlap"));
            }
            pw.append("\n\n"); //endline and make a blank row

            //Make the useless data at the top of Robo4 with Widefield and objective that aren't used
            if (imgParams.get("microscope").equals("Robo4")) {
                pw.append("Mode,Objective\nWidefield,20XELWD\n\n");
            }

            //add timepoints
            pw.append("Date,Time,Timepoint,Hour,Estimated Duration(hours)\n");
            for (int i = 0; i < calendarDates.size(); i++) {
                pw.append(calendarDates.get("T" + i) + ",");//date
                pw.append(time.get("T" + i + "date") + ",");//time
                pw.append(String.valueOf(i) + ","); //timepoint
                pw.append(time.get("hour" + i) + ","); // hour expecting 24hr timepoints for now
                pw.append(String.valueOf((double) duration));//duration
                pw.append("\n");
            }
            pw.append("\n");

            String include = "";
            if (!imgParams.get("microscope").equals("Robo3")) {
                pw.append("Well,isPositionFirst,Array,PFS,Channels,Exposures,Excitation Intensity,Z Height,Z Step Size,Objective,Overlap,Timelapse\n");
            } else if (imgParams.get("microscope").equals("Robo3")) {
                pw.append("Well,PositionFirst,Array,PFS,Channels,Exposure,Gains,Burst\n");
            }

            for (int i = 0; i <= wells.getNumberOfPositions() - 1; i++) {
                pw.append(wells.getPosition(i).getLabel() + ","); //wells
                pw.append(imgParams.get("positionFirst") + ",");
                pw.append(imgParams.get("mosaic") + ",");
                pw.append(imgParams.get("pfs") + ",");

                //excitation and emission for Robo3 is just a channel string
                //if(imgParams.get("microscope").equals("Robo3")) { pw.append(imgParams.get("channels") + ",");}
                pw.append(imgParams.get("channels") + ","); // channel is a string for all systems now
                //excitation and emission for Robo4 is in wavelength and filter bandpass
                if (!imgParams.get("microscope").equals("Robo3")) {
                    //pw.append(exEm[1]+",");
                    //pw.append(exEm[0]+",");
                    pw.append(imgParams.get("exposure") + ",");
                    pw.append(imgParams.get("intensity") + ",");
                    pw.append(imgParams.get("zheight") + ",");
                    pw.append(imgParams.get("zstep") + ",");
                    //pw.append("4000,"); //DiskSpeed Erase with new Robo4 code rollout
                    //pw.append("561 LP,");//remove this with the new Robo4 code rollout
                    //pw.append(exEm[3] +",");
                    //pw.append(exEm[2]+","); //remove this with the new Robo4 code rollout
                    //pw.append("long,"); //remove with the new Robo4 code rollout
                    pw.append(imgParams.get("obj") + ",");
                } else if (imgParams.get("microscope").equals("Robo3")) {
                    pw.append(imgParams.get("exposure") + ",");
                    pw.append(imgParams.get("gain") + ",");
                    pw.append(imgParams.get("burst"));
                }

                if (!imgParams.get("microscope").equals("Robo3")) {
                    pw.append(imgParams.get("overlap") + ",");
                    pw.append(imgParams.get("burst") + ",");
                }
                pw.append("\n");
            }
            pw.flush();
            pw.close();
            String attachment = F.getAbsolutePath() + "," + attach.getPath();
            if(imgParams.get("microscope").equals("Robo5")){
                sendEmail(imgParams.get("author"), attachment, F.getName(), "RoboticScopeQC@gmail.com,RoboticScope5@gmail.com");
            }else if(imgParams.get("microscope").equals("Robo4")) {
                sendEmail(imgParams.get("author"), attachment, F.getName(), "RoboticScopeQC@gmail.com,RoboticScope4@gmail.com");
            }else if(imgParams.get("microscope").equals("Robo3")) {
                sendEmail(imgParams.get("author"), attachment, F.getName(), "RoboticScopeQC@gmail.com,RoboticScope3@gmail.com");
            }else if(imgParams.get("microscope").equals("Robo2")) {
                sendEmail(imgParams.get("author"), attachment, F.getName(), "RoboticScopeQC@gmail.com,RoboticScope2@gmail.com");
            }else if(imgParams.get("microscope").equals("Robo6")) {
                sendEmail(imgParams.get("author"), attachment, F.getName(), "RoboticScopeQC@gmail.com,RoboticScope6@gmail.com");
            }
        }catch(FileNotFoundException FNE){IJ.log("ERROR: "+FNE);}
    }


    public void sendEmail(String author, String filepath, String filename, String CC){
        try {
            GoogleMail.Send("roboticscopeqc", "R0b0sc0p3QC", author, CC, filename, "Howdy,\n Generated template attached.\n\n Cheers,\n Mr. Roboto", filepath);
        }catch(Exception mex){
            IJ.log("Could not send message " + mex);
        }
    }

    private String getBarcode(){
        int rand = 0;
        while (rand < 1E7){
            rand =(int)(Math.random()*1E8);
        }
        return String.valueOf(rand);
    }

}
