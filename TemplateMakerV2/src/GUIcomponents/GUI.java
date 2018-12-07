package GUIcomponents;

import ij.IJ;
import ij.gui.DialogListener;
import ij.gui.GenericDialog;
import ij.gui.NonBlockingGenericDialog;

import javax.mail.MessagingException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * Created by elliot on 10/11/15.
 */
public class GUI implements ActionListener{
    private int numChannels;
    private String expType;
    public GenericDialog gd = new NonBlockingGenericDialog("Template Maker V2.0");
    private GenericDialog intensityDlg = new NonBlockingGenericDialog("Set Channel Intensities");
    private GenericDialog PSDlg = new NonBlockingGenericDialog("Set UV Exposure Settings");
    private final String[] authorlist = {
            " ",
            "Amela",
            "AshkanJ",
            "Ashmita",
            "Caitlyn",
            "IrinaE",
            "Jaslin",
            "Jen",
            "Jeremy",
            "JuliaK",
            "Mariah",
            "Michelle",
            "LisaE",
            "LisaJo",
            "LiseB",
            "Mel",
            "Nick",
            "Pasquale",
            "SaraM",
            "Seema",
            "Sid",
            "Elliot"};
    private final String[] SupportedExperiment = {
            "PhotoConversion",
            "Longitudinal",
            "Spectral-LinearSteps",
            "Spectral-Peaks"};
    private final String[] Robo2channels = {
            "Brightfield",
            "Epi_DAPI",
            "Epi_GFP",
            "Epi_RFP",
            "Epi_Cy5"};
    private final String[] Robo3channels = {
            "Brightfield",
            "FITC-DFTrCy5",
            "FITC_Bin4",
            "RFP-DFTrCy5",
            "DAPI",
            "Cy5",
            "CFP",
            "YFP",
            "CYFret",
            "Epi_Keima_G",
            "Open",
            "OptoPatch",
            "RFP-CYR",
            "RFP-EOS2",
            "GFP-RFP-FRET"};
    private final String[] Robo4channels = {
            "40XPh216",
            "Brightfield",
            "Confocal_BFP_RFP16-Accum4",
            "Confocal_BFP_RFP16-Accum5",
            "Confocal_BFP_RFP16-Accum8",
            "Confocal_DAPI16",
            "Confocal_GFP16",
            "Confocal_RFP16",
            "Confocal_Cy516",
            "Confocal_GFP_RFP16",
            "Confocal_GFP_Cy516",
            "Confocal_DAPI_RFP16",
            "Confocal_Keima447Excitation",
            "Confocal_Keima561Excitation",
            "Confocal_dKeima16",
            "Confocal_gKeima",
            "Confocal_rKeima",
            "Confocal_Qdot65516",
            "Confocal_YFP16",
            "Epi_DAPI16",
            "Epi_DAPI16-Bin2x2",
            "EPi_DAPI_RFP16",
            "Epi_GFP16",
            "Epi_GFP16-Bin2x2",
            "Epi_Quasar16",
            "Epi_YFP16",
            "Epi_RFP16",
            "Epi_RFP16-Bin2x2",
            "Epi_Cy516",
            "Epi_Cy516-Bin2x2"};
    private final String[] Robo5channels = {
            "Brightfield",
            "Epi_DAPI",
            "Epi_BFP",
            "Epi_CFP",
            "Epi_GFP",
            "Epi_YFP",
            "Epi_RFP",
            "Epi_mApple",
            "Epi_mKate",
            "Epi_Cy5"};
    private final String[] Robo6channels ={
            "Brightfield",
            "Epi_DAPI",
            "Epi_BFP",
            "Epi_CFP",
            "Epi_GFP",
            "Epi_YFP",
            "Epi_RFP",
            "Epi_Cy5"};
    private String[] Robo5objectives = {
            "10X",
            "20X",
            "40X"};
    private String[] Robo6objectives = {
            "10X",
            "20X",
            "40X"};
    private final String[] Robo4objectives = {
            "10X",
            "20X",
            "20XPh1",
            "40X",
            "40XPh2",
            "60X"};
    private final String[] Robo3objecitves = {
            "10XSFluor",
            "20XELWD",
            "40XELWD"};
    private final String[] Robo2objectives = {
            "10X",
            "20X",
            "40X"};
    private final String[] wellCount = {
            "48",
            "96",
            "384"};
    private final String[] robo3Camera = {
            "Andor888",
            "Zyla42"};
    private final String[] plateType = {
            "CCB",
            "CCBExtStg",
            "TPP",
            "384CCB",
            "CCBGlass",
            "CCBSlice",
            "MEA48Well"};
    private final String[] Stack = {"1","2"};
    private final String[] Shelf = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22"};
    private final String[] microscope = {
            "Robo3",
            "Robo4",
            "Robo5",
            "Robo6"};
    private final String[] arraySizes = {
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9",
            "10"};
    private final String[] positionFirst = {
            "TRUE",
            "FALSE"};
    private final String[] pfsPerTile = {
            "TRUE",
            "FALSE"};
    private Map<String, String> imgParams = new HashMap<String, String>();
    private String scopeChoice;

    public GUI() {
        GenericDialog initWindow = new GenericDialog("TemplateMakerV2.0");
        initWindow.addNumericField("Number of Channels to Image",1,0);
        initWindow.addChoice("Microscope",microscope,microscope[1]);
        initWindow.addChoice("Experiment Type",SupportedExperiment,SupportedExperiment[1]);
        initWindow.showDialog();
        numChannels = (int)initWindow.getNextNumber();
        scopeChoice = initWindow.getNextChoice();
        expType  = initWindow.getNextChoice();
        if (initWindow.wasOKed()){
            MainWindow(numChannels);
        }
    }

    public void MainWindow(int numChan){
        //Experimental conditions
        //gd.addStringField("Experiment Name", "Experiment Name");
        gd.addChoice("Author",authorlist, null);
        //gd.addStringField("Personal Barcode", "00000");
        gd.addChoice("Plate", plateType, plateType[0]);
        gd.addChoice("Stack",Stack,Stack[0]);
        gd.addChoice("Shelf",Shelf,Shelf[0]);
        //gd.addChoice("WellCount",wellCount,wellCount[0]);

        //Per well conditions
        //position,mosaic,channels,Zheight,Zstep,Objectives,#img,interval,overlap
        gd.addChoice("Position First", positionFirst, positionFirst[0]);
        gd.addChoice("PFS per Tile",pfsPerTile,pfsPerTile[0]);
        if(scopeChoice.equals("Robo4") || scopeChoice.equals("Robo5") || scopeChoice.equals("Robo6")) {
            gd.addChoice("Mosaic Size X", arraySizes, arraySizes[2]);
            gd.addChoice("Mosaic Size Y", arraySizes, arraySizes[2]);
        }else if(scopeChoice.equals("Robo3")){
            gd.addChoice("Mosaic Size",arraySizes,arraySizes[2]);
        }else if(scopeChoice.equals("Robo2")) {
            gd.addChoice("Mosaic Size", arraySizes, arraySizes[2]);
        }

        for(int chan=0;chan<numChan;chan++){
            if (scopeChoice.equals("Robo5") && expType.equals("Spectral-LinearSteps")) {
                gd.addSlider("Range"+(chan+1)+"Starting Wavelength(nm)",390,700,400);
                gd.addSlider("Range"+(chan+1)+"Ending Wavelength(nm)",390,700,700);
                gd.addSlider("Range"+(chan+1)+"StepSize(nm)",4,1000,15);
            }else if(scopeChoice.equals("Robo5") && expType.equals("Spectral-Peaks")){
                for (int p=0;p<numChan;p++) {
                    gd.addNumericField("FluorPeak" + (p + 1), 390, 0, 10, "nm");
                }
            }else if (scopeChoice.equals("Robo6")) {
                gd.addChoice("Channel" + (chan + 1), Robo6channels, Robo6channels[chan]);
            }else if (scopeChoice.equals("Robo5")){
                gd.addChoice("Channel" + (chan + 1), Robo5channels, Robo5channels[chan]);
            } else if(scopeChoice.equals("Robo4")) {
                gd.addChoice("Channel" + (chan + 1), Robo4channels, Robo4channels[chan]);
            }else if(scopeChoice.equals("Robo3")){
                gd.addChoice("Channel" + (chan + 1), Robo3channels, Robo3channels[chan]);
            }else if(scopeChoice.equals("Robo2")) {
                gd.addChoice("Channel" + (chan + 1), Robo2channels, Robo2channels[chan]);
            }
            gd.addNumericField("Exposure", 0, 0); //Exposure
            if (scopeChoice.equals("Robo3")){
                gd.addNumericField("Gain", 4, 0);
            }
            gd.addNumericField("PFS Offset", 999, 0);
            if(!scopeChoice.equals("Robo3")) {
                //gd.addNumericField("Intensity" + (chan + 1) + " (%)", 100, 0);
                gd.addNumericField("Z-height", 0, 0);
                gd.addNumericField("ZStepSize", 1, 2);
            }
            gd.addNumericField("Number of Images", 1, 0);
            gd.addNumericField("Time Interval(ms)", 0, 0);

            if (scopeChoice.equals("Robo5") && expType.equals("Spectral-Peaks")) { break; }
        }

        if(scopeChoice.equals("Robo3")) {
            gd.addChoice("Objectives", Robo3objecitves, Robo3objecitves[0]);
            gd.addChoice("Camera",robo3Camera,robo3Camera[0]);
        }else if (scopeChoice.equals("Robo4")){
            gd.addChoice("Objectives", Robo4objectives, Robo4objectives[0]);
        }else if (scopeChoice.equals("Robo2")) {
            gd.addChoice("Objectives", Robo2objectives, Robo2objectives[0]);
        }else if (scopeChoice.equals(("Robo5"))) {
            gd.addChoice("Objectives", Robo5objectives, Robo5objectives[0]);
        }else if (scopeChoice.equals(("Robo6"))) {
            gd.addChoice("Objectives", Robo6objectives, Robo6objectives[0]);
        }

        gd.addNumericField("Overlap", 0.1, 2);

        //show the dialog
        gd.showDialog();

        //add a listener for all the features
        gd.addDialogListener(new DialogListener() {
            @Override
            public boolean dialogItemChanged(GenericDialog genericDialog, AWTEvent awtEvent) {
                return false;
            }
        });

        if(gd.wasOKed()){
            if(!scopeChoice.equals("Robo3")){
                setIntensity();
            }
            imageParameters();
            if (expType.equals("PhotoConversion") && scopeChoice.equals("Robo6")) {
                setUVExposure();
            }
            new CalendarChooser(imgParams);}
        if(gd.wasCanceled()){
            return;}

    }

    public void setIntensity(){
        intensityDlg.addSlider("Violet_LED",0,100,0);
        intensityDlg.addSlider("Blue_LED",0,100,0);
        intensityDlg.addSlider("Cyan_LED",0,100,0);
        intensityDlg.addSlider("Teal_LED",0,100,0);
        intensityDlg.addSlider("Green_LED",0,100,0);
        intensityDlg.addSlider("Red_LED",0,100,0);
        if (scopeChoice.equals("Robo4")) {
            intensityDlg.addSlider("405nm_Laser", 0, 100, 0);
            intensityDlg.addSlider("447nm_Laser", 0, 100, 0);
            intensityDlg.addSlider("488nm_Laser", 0, 100, 0);
            intensityDlg.addSlider("516nm_Laser", 0, 100, 0);
            intensityDlg.addSlider("561nm_Laser", 0, 100, 0);
            intensityDlg.addSlider("642nm_Laser", 0, 100, 0);
        }
        intensityDlg.showDialog();

        if (intensityDlg.wasOKed()){
            System.out.println("ExpType: "+expType);
            System.out.println("Scope: "+scopeChoice);
            //imageParameters();
        }
        if (intensityDlg.wasCanceled()) {
            return;
        }
    }

    public void setUVExposure(){
        PSDlg.addNumericField("Exposure",0000,0,15,"ms");
        GregorianCalendar d = new GregorianCalendar();
        String[] ampm = {"AM","PM"};
        PSDlg.addStringField("Date",d.get(Calendar.MONTH)+1+"/"+d.get(Calendar.DAY_OF_MONTH)+"/"+d.get(Calendar.YEAR));
        PSDlg.addStringField("Time",d.get(Calendar.HOUR)+":00"+" "+ampm[d.get(Calendar.AM_PM)]);
        PSDlg.showDialog();

        if(PSDlg.wasOKed()) {
            try {
                // draft an email to Robo6 with PS information
                String msg = "Exposure:" + PSDlg.getNextNumber()+
                        ", Date: " + PSDlg.getNextString()+
                        ", Time: " + PSDlg.getNextString()+
                        ", Stack: " +imgParams.get("stack")+
                        "Shelf: " +imgParams.get("shelf");
                GoogleMail.Send("RoboticscopeQC","R0b0sc0p3QC","Robo6","Photoconversion",msg);
                IJ.log("PhotoConversion scheduled \n"+msg);
            }catch(MessagingException mex){
                IJ.log("Could not send email: "+mex);
            }
        }
        if(PSDlg.wasCanceled()){
            System.exit(0);
        }


    }

    public void imageParameters(){
        //imgParams.put("name",gd.getNextString()); //Name
        imgParams.put("author",gd.getNextChoice()); //Author
        //imgParams.put("barcode", getBarcode()); //gd.getNextString()); // Barcode
        imgParams.put("microscope", scopeChoice); //Microscope
        imgParams.put("expType", expType);
        imgParams.put("plate",gd.getNextChoice()); // Plate
        imgParams.put("stack",gd.getNextChoice()); // Stack
        imgParams.put("shelf",gd.getNextChoice()); // Shelf
        imgParams.put("positionFirst",gd.getNextChoice()); // position first
        imgParams.put("pfsPerTile",gd.getNextChoice()); // PFS per Tile
        if (scopeChoice.equals("Robo4") || scopeChoice.equals("Robo5") || scopeChoice.equals("Robo6")) {
            imgParams.put("mosaic", gd.getNextChoice()+"x"+gd.getNextChoice()); // Mosaic XY axis
        }else if(scopeChoice.equals("Robo3") || scopeChoice.equals("Robo2")){
            imgParams.put("mosaic", gd.getNextChoice()); // Mosaic
        }
        String Chan = "";
        String Exp = "";
        String Gain = "";
        String Intensity = "";
        String Zheight="";
        String Zstep="";
        String timelapse = "";
        String PFS = "";
        for(int i = 0;i<numChannels;i++){
            if (scopeChoice.equals("Robo5") && expType.equals("Spectral-LinearSteps")){
                Chan+=(int)gd.getNextNumber()+":"+(int)gd.getNextNumber()+":"+(int)gd.getNextNumber();
            }else if (scopeChoice.equals("Robo5") && expType.equals("Spectral-Peaks")){
                for (int p=0;p<numChannels;p++) {
                    Chan += (int) gd.getNextNumber();
                    if (p < numChannels - 1) Chan += ":"; // do not place a ":" after last peak value
                }
                System.out.println(Chan);
            }else {
                Chan += gd.getNextChoice();
            }
            Exp+=(int)gd.getNextNumber();
            if(scopeChoice.equals("Robo3")) {
                Gain += (int)gd.getNextNumber();
            }
            PFS+=String.valueOf((int)gd.getNextNumber());
            if (!scopeChoice.equals("Robo3")) {
                if(i==0) {
                    for (int j = 0; j < intensityDlg.getSliders().size(); j++) {
                        Intensity += (int)intensityDlg.getNextNumber();
                        if (j < intensityDlg.getSliders().size() - 1) {
                            Intensity += ":";
                        }
                    }
                }
                //ij.IJ.log("Intensity String: " + Intensity);
                //Intensity += String.valueOf((int)gd.getNextNumber());
                Zheight += String.valueOf(gd.getNextNumber());
                Zstep += String.valueOf(gd.getNextNumber());
            }
            timelapse += String.valueOf((int)gd.getNextNumber()) + ":" + String.valueOf((int)gd.getNextNumber());

            if(scopeChoice.equals("Robo5") && expType.equals("Spectral-Peaks")){break;} //exit after collecting only one round of numbers leaving off the ";"
            if(i<numChannels-1){Chan+=";"; Exp+=";"; Gain+=";"; Zstep+=";"; Zheight+=";"; PFS+=";"; timelapse+=";";}

        }
        imgParams.put("channels",Chan);
        imgParams.put("exposure",Exp);
        imgParams.put("pfs", PFS);
        if(!scopeChoice.equals("Robo3")) {
        //    for (int i=0;i<intensityDlg.getSliders().size();i++){
        //        Intensity+=intensityDlg.getSliders().get(i).toString();
        //    }
            imgParams.put("intensity", Intensity);
            imgParams.put("zheight", Zheight); // Zheight
            imgParams.put("zstep", Zstep); // Zstep
        }else imgParams.put("gain",Gain);
        imgParams.put("obj", gd.getNextChoice()); // Objective
        if(scopeChoice.equals("Robo3")){imgParams.put("camera",gd.getNextChoice());} //Camera
        imgParams.put("burst",timelapse);
        imgParams.put("overlap", String.valueOf(gd.getNextNumber())); // Overlap
    }


    public void actionPerformed(ActionEvent ae){

    }
}
