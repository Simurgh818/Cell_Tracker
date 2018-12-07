import ij.gui.GenericDialog;

import hcs.SiteGenerator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by elliot on 7/8/17.
 */

public class plateSelectionWin {
    GenericDialog gd = new GenericDialog("PlateSelection window");
    SiteGenerator sg;

    public plateSelectionWin() {
        sg = new SiteGenerator();
        sg.setPosition.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sg.setPositionList();
                System.out.println("SettingPositions");
                sg.setPosition.setText("GettingPositions!");
                sg.setVisible(false);

                System.out.println(sg.Positions.getPosition(0).getLabel());
            }
        });
    }

    public void run(){
        sg.setVisible(true);
    }
}
