
import ij.gui.GenericDialog;
import ij.gui.NonBlockingGenericDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * Created by elliot on 7/6/17.
 */
public class paramWindow{
    GenericDialog gd;
    JTabbedPane tp = new JTabbedPane();
    Panel p;
    Panel subp; // sub panels
    int channels=0, count=0;


    ActionListener listen= new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
        }
    };

    private final String[] SupportedExperiment = {
            "PhotoConversion",
            "Longitudinal",
            "Spectral"};
    private final String[] Robo6channels = {
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
            "Brightfield16",
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
            "Epi_RFP16",
            "Epi_RFP16-Bin2x2",
            "Epi_Cy516",
            "Epi_Cy516-Bin2x2"};
    private final String[] Robo5channels = {
            "Epi_DAPI",
            "Epi_BFP",
            "Epi_CFP",
            "Epi_GFP",
            "Epi_YFP",
            "Epi_RFP",
            "Epi_mApple",
            "Epi_mKate",
            "Epi_Cy5"};
    private String[] Robo5objectives = {
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
    private final String[] Robo6objectives = {
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
    private final String[] arraySizes = {
            "1",
            "2",
            "3",
            "4",
            "5"};
    private final String[] positionFirst = {
            "TRUE",
            "FALSE"};
    private final String[] pfsPerTile = {
            "TRUE",
            "FALSE"};
    public Map<String, String> imgParams = new HashMap<String, String>();
    private String scopeChoice;


    public paramWindow(String microscope, int channels) {
        gd = new NonBlockingGenericDialog("TemplateMakerV3");
        int scope = Integer.valueOf(microscope.replace("Robo",""));
        this.channels = channels;
        switch(scope){
            case 3 : setRobo3Params(); break;
            case 4 : setRobo4Params(); break;
            case 5 : setRobo5Params(); break;
            case 6 : setRobo6Params(); break;
        }
    }


    public Map<String, String> getParams(String microscope){
        int scope = Integer.valueOf(microscope.replace("Robo",""));
        this.channels = channels;
        switch(scope){
            case 3 : getRobo3Params(); return imgParams;
            case 4 : getRobo4Params(); return imgParams;
            case 5 : getRobo5Params(); return imgParams;
            case 6 : getRobo6Params(); return imgParams;
        }
       return imgParams;
    }



    public void run(){
        gd.showDialog();
        if(gd.wasOKed()){
            new plateSelectionWin();
        }
    }

    private void setRobo3Params(){
        gd.addStringField("Experiment","Robo3 Experiment Name");
        gd.addChoice("IsPositionFirst",positionFirst,positionFirst[0]);
        gd.addChoice("PFSperTile",pfsPerTile,pfsPerTile[0]);
        gd.addChoice("ArraySize(square)",arraySizes,arraySizes[0]);
        gd.addChoice("Channel",Robo3channels,Robo3channels[0]);
        gd.addChoice("Camera", robo3Camera,robo3Camera[0]);
        gd.addNumericField("EMGain(iXon only)",4,0);
        gd.addChoice("Objective",Robo3objecitves,Robo3objecitves[0]);
        gd.addNumericField("Overlap",0,0);

    }

    public void getRobo3Params(){
        imgParams.put("IsPositionFirst",gd.getNextChoice());
        imgParams.put("pfsPerTile",gd.getNextChoice());
        imgParams.put("arraySize",gd.getNextChoice());
        imgParams.put("channel",gd.getNextChoice());
        imgParams.put("camera",gd.getNextChoice());
        imgParams.put("gain",String.valueOf(gd.getNextNumber()));
        imgParams.put("objective",gd.getNextChoice());
        imgParams.put("overlap",String.valueOf(gd.getNextNumber()));
    }

    private void setRobo4Params(){
        gd.addStringField("Experiment Name","Robo4 Experiment Name");
        gd.addChoice("IsPositionFirst",positionFirst,positionFirst[0]);
        gd.addChoice("PFSperTile",pfsPerTile,pfsPerTile[0]);
        gd.addChoice("ArraySize(square)",arraySizes,arraySizes[0]);
        gd.addChoice("Channel",Robo4channels,Robo4channels[0]);
        gd.addNumericField("ZStack Height",0,0);
        gd.addNumericField("ZStack Step Size",1,0);
        gd.addChoice("Objective",Robo4objectives,Robo4objectives[0]);
        gd.addNumericField("Overlap",0,0);

    }

    public void getRobo4Params(){
        imgParams.put("IsPositionFirst",gd.getNextChoice());
        imgParams.put("pfsPerTile",gd.getNextChoice());
        imgParams.put("arraySize",gd.getNextChoice());
        imgParams.put("channel",gd.getNextChoice());
        imgParams.put("zheight",String.valueOf(gd.getNextNumber()));
        imgParams.put("zstep",String.valueOf(gd.getNextNumber()));
        imgParams.put("objective",gd.getNextChoice());
        imgParams.put("overlap",String.valueOf(gd.getNextNumber()));
    }

    private void setRobo5Params(){
        gd.addStringField("Experiment","Robo5 Experiment");
        gd.addChoice("IsPositionFirst",positionFirst,positionFirst[0]);
        gd.addChoice("PFSperTile",pfsPerTile,pfsPerTile[0]);
        gd.addChoice("ArraySize(square)",arraySizes,arraySizes[0]);
        gd.addChoice("Channel",Robo5channels,Robo5channels[0]);
        gd.addChoice("Objective",Robo5objectives,Robo5objectives[0]);
        gd.addNumericField("Overlap",0,0);

    }


    public void getRobo5Params(){
        imgParams.put("stack",gd.getNextChoice());
        imgParams.put("shelf",gd.getNextChoice());
        imgParams.put("IsPositionFirst",gd.getNextChoice());
        imgParams.put("pfsPerTile",gd.getNextChoice());
        imgParams.put("arraySize",gd.getNextChoice());
        imgParams.put("channel",gd.getNextChoice());
        imgParams.put("objective",gd.getNextChoice());
        imgParams.put("overlap",String.valueOf(gd.getNextNumber()));
    }


    private void setRobo6Params(){
        gd.addStringField("Experiment","Robo6 Experiment");
        gd.addChoice("IsPositionFirst",positionFirst,positionFirst[0]);
        gd.addChoice("PFSperTile",pfsPerTile,pfsPerTile[0]);
        gd.addChoice("ArraySize(square)",arraySizes,arraySizes[0]);
        gd.addChoice("Channel",Robo6channels,Robo6channels[0]);
        gd.addChoice("Objective",Robo6objectives,Robo6objectives[0]);
        gd.addNumericField("Overlap", 0,0);
    }


    public void getRobo6Params(){
        imgParams.put("IsPositionFirst",gd.getNextChoice());
        imgParams.put("pfsPerTile",gd.getNextChoice());
        imgParams.put("arraySize",gd.getNextChoice());
        imgParams.put("channel",gd.getNextChoice());
        imgParams.put("objective",gd.getNextChoice());
        imgParams.put("overlap",String.valueOf(gd.getNextNumber()));
    }
}
