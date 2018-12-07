import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.PrintWriter;
import java.util.Iterator;

/**
 * Created by elliot on 7/17/17.
 */
public class templateMaker {
    private static JSONObject templateObj=new JSONObject();
    private static JSONObject jo = new JSONObject();
    private static File f = null;
    private static PrintWriter pw;

    public static void make(){
        try {
            //System.out.println(jo.toString());
            f = new File(jo.getJSONObject("init").getString("savePath"));
            Iterator itr = jo.keys();
            while (itr.hasNext()) {
                Object chan = itr.next();
                System.out.println(chan.toString());

            }
        }catch(JSONException je){
            System.out.println("Error parsing JSONObject: " + je);
        }

    }

    private void Headers(){
        try {

            if (!templateObj.getJSONObject("microscope").equals("Robo3")) {
                templateObj.put("experimentHeader","Experiment Name,Barcode,Stack,Shelf,Author,Description,Plate,Well count,Microscope,Image Folder,Incubator,PFSPerTile\n");
                templateObj.put("paramHeader","Well,isPositionFirst,Array,PFS,Channels,Exposures,Excitation Intensity,Z Height,Z Step Size,Objective,Overlap,Timelapse\n");
            } else if (templateObj.getJSONObject("microscope").equals("Robo3")) {
                templateObj.put("experimentHeader","Experiment Name,Barcode,Stack,Shelf,Author,Description,Plate,Objective,Camera,Overlap\n");
                templateObj.put("paramHeader", "Well,PositionFirst,Array,PFS,Channels,Exposure,Gains,Burst\n");
            }
        }catch(JSONException je){
            System.out.println("JSONException caught while extracting experimentHeader: "+je);
        }
    }

    private void experimentData(){
        try{

        }catch(JSONException je){
            System.out.println("JSONException caught while writing experimentData: "+je);
        }
    }

    private void schedulerData(){
        try{

        }catch(JSONException je){
            System.out.println("JSONException caught while writing schedulerData: "+je);
        }

    }

    private void robo3ParamData(){
        try{


        }catch(JSONException je){
            System.out.println("JSONException caught while writing robo3ParamData: "+je);
        }

    }

    private void paramData(){
        try{

        }catch(JSONException je){
            System.out.println("JSONException caught while writing paramData: "+je);
        }

    }

    private void writeFile(){
        try{

        }catch(JSONException je){
            System.out.println("JSONException caught while writing file to disk: "+je);
        }

    }
}
