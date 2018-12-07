import ij.gui.GenericDialog;
import ij.gui.NonBlockingGenericDialog;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by elliot on 7/6/17.
 */
public class initWindow {

    GenericDialog gd = new NonBlockingGenericDialog("Experiment Type");
    private final String[] investigator = { " ",
                    "Alicia",
                    "Amela",
                    "Ashmita",
                    "Caitlyn",
                    "IrinaE",
                    "Jaslin",
                    "Jen",
                    "Jeremy",
                    "JuliaK",
                    "Kelly",
                    "Mariah",
                    "Lisa",
                    "LisaJo",
                    "LiseB",
                    "Mel",
                    "Nick",
                    "Pasquale",
                    "Piyush",
                    "SaraM",
                    "Sid",
                    "Elliot"};
    private final String[] scopes = {"Robo3","Robo4","Robo5","Robo6"};
    private final String[] type = {"Longitudinal","Photoswitching","Spectral"};
    private final String[] channels = {"1","2","3","4"};
    private final String[] plateType = {
            "CCB",
            "CCBExtStg",
            "TPP",
            "384CCB",
            "CCBGlass",
            "CCBSlice",
            "MEA48Well"};
    private final String[] manual={"FALSE","TRUE"};
    private final String[] Stack = {"1","2"};
    private final String[] Shelf = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22"};

    public Map initMap = new HashMap();

    public void run(){
        gd.addChoice("Investigator",investigator, investigator[0]);
        gd.addChoice("Roboscope: ", scopes, scopes[0]);
        gd.addChoice("Experiment Type: ", type, type[0]);
        gd.addChoice("Plate Type", plateType,plateType[0]);
        gd.addChoice("# of channels: ", channels, channels[0]);
        gd.addChoice("Manual Run", manual, manual[0]);
        gd.addChoice("Stack",Stack,Stack[0]);
        gd.addChoice("Shelf",Shelf,Shelf[0]);
        gd.showDialog();
    }

    public void getValues(){
        initMap.put("Investigator", gd.getNextChoice());
        initMap.put("Roboscope", gd.getNextChoice());
        initMap.put("ExperimentType", gd.getNextChoice());
        initMap.put("PlateType",gd.getNextChoice());
        initMap.put("#Channels",gd.getNextChoice());
        initMap.put("ManualRun",gd.getNextChoice());
        initMap.put("Stack",gd.getNextChoice());
        initMap.put("Shelf",gd.getNextChoice());
    }

}