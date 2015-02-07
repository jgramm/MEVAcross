package actions;

import ui.DataPanel;
import ui.PathwayPanel;
import algorithm.*;

import java.awt.event.*;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class RunMevaAction implements ActionListener{

	public void actionPerformed(ActionEvent ae){
		
		// check for two selected columns
		if(DataPanel.dataTable.getSelectedColumnCount() != 2){
			JOptionPane.showMessageDialog(new JFrame(), "Please select two columns to run MEVA analysis on");
			return;
		}
		
		List<Pathway> analysis;
		
		LinkedHashMap<String,Double> data = ui.DataPanel.getLinkedHashMapData();
		DataStorage.iterations = 1001;
		try {
			analysis = RunMEVAColumn.run(data, "ADD A SAMPLE NAME IN RunMevaAction");
			PathwayPanel.acceptPathways(analysis);
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			
			ex.printStackTrace();
		}
	
		
	}
	
}
