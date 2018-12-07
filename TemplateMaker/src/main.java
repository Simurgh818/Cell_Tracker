import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

import static org.micromanager.utils.JavaUtils.sleep;

/**
 * Created by elliot on 7/6/17.
 */
public class main {

    static Vector iwV = new Vector();
    static initWindow iw;
    static Vector pwV = new Vector();
    static paramWindow pw;
    static plateSelectionWin psw;
    static ArrayList<String> al = new ArrayList<String>();
    static Map paramMap;
    static JSONObject jo=new JSONObject();
    private static int channels=0, chan=0;

    public static void main(String[] args) {
        // Ask initial questions about the experiment
        createInit();
        // Ask what parameters will be needed for this experiment and where they are located on the plate
        createParamWin(chan+1);
        // Pull the schedule from GoogleCalendar
        //createCalWin();
        // Make the template
        createTemplate();
        // Make the manifest file
        createManifest();

    }

    public static void createInit(){
        try {
            iw = new initWindow();
            iw.run();
            if (iw.gd.wasOKed()) {
                iw.getValues();
                jo.put("init", new JSONObject(iw.initMap));
                channels = Integer.valueOf(jo.getJSONObject("init").get("#Channels").toString());
                ij.IJ.log("Now creating " + channels + " channels.");
            }
        }catch(JSONException je){
            System.out.println("Error adding initmap to JSONObject: "+je);
        }
    }

    private static void createParamWin(int chanNum){
        try {
            String scope = jo.getJSONObject("init").get("Roboscope").toString();
            pw = new paramWindow(scope, chanNum);
            pw.run();
            if (pw.gd.wasOKed()) {
                paramMap = pw.getParams(scope);
                //map these parameters to the plate
                createPlateMap();
            }
        }catch(JSONException je){
            System.out.println("Error while parsing JSONObject: "+je);
        }
    }

    private static  void createPlateMap(){
        try {
            psw = new plateSelectionWin();
            psw.run();
            while (psw.sg.isVisible()){
                //System.out.println("Waiting....");
                sleep(300);
            }
            ArrayList a = new ArrayList();
            for (int i=0;i<psw.sg.Positions.getPositions().length;i++) {
                a.add(psw.sg.Positions.getPosition(i).getLabel());
            }
            paramMap.put("wells",new JSONArray(a));
            jo.put("Channel"+(chan+1), new JSONObject(paramMap));
            chan+=1;
            if (chan<channels) {
                createParamWin(chan);
            }
        }catch(JSONException je){
            System.out.println("Could not add entry to main JSON object:"+je);
        }
    }

    private static void createCalWin(){
        //todo use GCalendar to pull already scheduled dates?
        ij.IJ.log("Scheduling the plate");
    }


    private static void createManifest(){

    }

    private static void createTemplate(){
        templateMaker.make(jo);
    }
}
