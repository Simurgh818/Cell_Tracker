package GUIcomponents;

import ij.gui.NonBlockingGenericDialog;


/**
 * Created by elliot on 2/2/17.
 **/

public class IntensityGUI {
    private NonBlockingGenericDialog intensityDlg = new NonBlockingGenericDialog("Set Intensity");
    private String intensityValues = "";

    public IntensityGUI(String scopeChoice){
        if(scopeChoice.equals("Robo4")) { EpiConfocalIntensity();}
        else if (scopeChoice.equals("Robo5")) { EpiIntensity();}
        else if (scopeChoice.equals("Robo2") || scopeChoice.equals("Robo3")){ LambdaXL();}
    }

    private String LambdaXL(){
        intensityValues = "0:0:0:0:0:0";
        return intensityValues;
    }

    private void EpiIntensity(){
        // Create the GUI for only settings for SpectraX
        intensityDlg.addSlider("Violet_LED",0,100,0);
        intensityDlg.addSlider("Blue_LED",0,100,0);
        intensityDlg.addSlider("Cyan_LED",0,100,0);
        intensityDlg.addSlider("Teal_LED",0,100,0);
        intensityDlg.addSlider("Green_LED",0,100,0);
        intensityDlg.addSlider("Red_LED",0,100,0);

        intensityDlg.showDialog();

        if (intensityDlg.wasOKed()){
            intensityValues = readValues();
        }
        if (intensityDlg.wasCanceled()) {
            System.exit(0);
        }
    }

    private void EpiConfocalIntensity() {
        intensityDlg.addSlider("Violet_LED", 0, 100, 0);
        intensityDlg.addSlider("Blue_LED", 0, 100, 0);
        intensityDlg.addSlider("Cyan_LED", 0, 100, 0);
        intensityDlg.addSlider("Teal_LED", 0, 100, 0);
        intensityDlg.addSlider("Green_LED", 0, 100, 0);
        intensityDlg.addSlider("Red_LED", 0, 100, 0);
        intensityDlg.addSlider("405nm_Laser", 0, 100, 0);
        intensityDlg.addSlider("447nm_Laser", 0, 100, 0);
        intensityDlg.addSlider("488nm_Laser", 0, 100, 0);
        intensityDlg.addSlider("516nm_Laser", 0, 100, 0);
        intensityDlg.addSlider("561nm_Laser", 0, 100, 0);
        intensityDlg.addSlider("642nm_Laser", 0, 100, 0);

        intensityDlg.showDialog();

        if (intensityDlg.wasOKed()) {
            intensityValues = readValues();
        }
        if (intensityDlg.wasCanceled()) {
            System.exit(0);
        }
    }

    private String readValues(){
        String intensity = "";
        // Read in the intensity values and generate the correct
        for (int j = 0; j < intensityDlg.getSliders().size(); j++) {
            intensity += (int)intensityDlg.getNextNumber();
            if (j < intensityDlg.getSliders().size() - 1) {
                intensity += ":";
            }
        }
        return intensity;
    }
}
