package GUIcomponents;

import hcs.SiteGenerator;

import ij.gui.GenericDialog;

import java.util.*;

/**
 * Created by elliot on 11/1/15.
 */
public class CalendarChooser {
    public GenericDialog calendar = new GenericDialog("Timepoint Dates and times");
    public int timepoints;
    private double duration;
    public double Interval;
    public GregorianCalendar today = new GregorianCalendar();
    private String[] startDate;
    private String[] startTime;
    private String[] startTimeAMPM;
    private String[] AMPMstring = {"AM","PM"};
    public Map<String,String> imgParams;
    public Map<String,String> calendarDates = new HashMap<String,String>();
    public Map<String,String> time = new HashMap<String,String>();

    public CalendarChooser(Map<String,String> imgParams) {
        this.imgParams = imgParams;
        GenericDialog dateChooser = new GenericDialog("Time-Points");
        dateChooser.addNumericField("Number of timepoints", 7, 0);
        dateChooser.addNumericField("Interval between timepoints(hrs)",24,0);
        dateChooser.addNumericField("Duration of each timepoint",0.5,1);
        //Month +1 makes Human readable
        dateChooser.addStringField("Start Date",(1+today.get(Calendar.MONTH))+"/"+today.get(Calendar.DAY_OF_MONTH)+"/"+today.get(Calendar.YEAR));
        dateChooser.addStringField("Start Time", today.get(Calendar.HOUR) + ":" + "00" + " " + AMPMstring[today.get(Calendar.AM_PM)]);

        dateChooser.showDialog();
        timepoints = (int)dateChooser.getNextNumber();
        Interval = dateChooser.getNextNumber();
        duration = dateChooser.getNextNumber();
        startDate = dateChooser.getNextString().split("/");
        startTimeAMPM = dateChooser.getNextString().split(" "); //split AMPM from time
        startTime = startTimeAMPM[0].split(":");

        //set the start time according to what was input by the user
        today.set(Calendar.YEAR, Integer.parseInt(startDate[2]));
        today.set(Calendar.MONTH, Integer.parseInt(startDate[0])-1); //subtract 1 for machine processing
        today.set(Calendar.DAY_OF_MONTH, Integer.parseInt(startDate[1]));
        today.set(Calendar.HOUR, Integer.parseInt(startTime[0]));
        today.set(Calendar.MINUTE, Integer.parseInt(startTime[1]));
        if(startTimeAMPM[1].equals("AM")){
            //IJ.log("Setting time to AM");
            today.set(Calendar.AM_PM,Calendar.AM);} //separated AMPM AM==1
        else{
            //IJ.log("Setting time to PM");
            today.set(Calendar.AM_PM,Calendar.PM);} //separated AMPM
        today.getTime();
        if (dateChooser.wasOKed()){
            MainWindow();
        }
    }

    public void MainWindow(){
        //JPanel panel = new JPanel(new GridLayout(timepoints,3));
        //panel.add(new JScrollBar(Adjustable.VERTICAL));
        //panel.add(new JScrollBar(Adjustable.VERTICAL,0,timepoints,0,timepoints));
        for(int t=0;t<timepoints;t++){
            if (t>0) {
                today.add(Calendar.MINUTE,(int)(Interval*60.0));
                //today.add(Calendar.HOUR, hourInterval);
            }
            today.getTime();
            String year = String.valueOf(today.get(Calendar.YEAR));
            String month = String.valueOf(today.get(Calendar.MONTH)+1); // add one for readability
            String day = String.valueOf(today.get(Calendar.DAY_OF_MONTH));
            String hour = String.valueOf(today.get(Calendar.HOUR));
            int minuteInt = today.get(Calendar.MINUTE);
            String minute = String.format("%02d",minuteInt);
            int AMPM = today.get(Calendar.AM_PM);

            //panel.add(new JLabel("T"+t));
            //panel.add(new JTextField(month + "/" + day + "/" + year,10));
            calendar.addStringField("T" + t, month + "/" + day + "/" + year);
            //panel.add(new JTextField(hour + ":" + minute + " " +AMPMstring[AMPM],10));
            calendar.addStringField("time" + t, hour + ":" + minute + " " +AMPMstring[AMPM]);
        }
        //calendar.add(panel);
        //calendar.add(new JScrollBar(Adjustable.VERTICAL,0,10,0,timepoints));


        calendar.showDialog();
        calendar.enableYesNoCancel("Continue","Cancel");
        if (calendar.wasOKed()){
            getDates();
            SiteGenerator sg = new SiteGenerator(imgParams,calendarDates,time,duration);
            sg.setVisible(true);
        }
        if (calendar.wasCanceled()){
            return;
        }
    }

    public void getDates(){
        for(int i=0;i<timepoints;i++) {
            String timepoint = "T"+String.valueOf(i);
            String timepointTime = timepoint+"date";
            String hour = String.valueOf(i*Interval);
            calendarDates.put(timepoint, calendar.getNextString());
            time.put(timepointTime,calendar.getNextString());
            time.put("hour"+i,hour);
        }
    }
}
