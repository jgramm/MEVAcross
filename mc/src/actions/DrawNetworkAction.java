package actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import ui.PathwayTableModel;
import ui.PathwayPanel;

public class DrawNetworkAction implements ActionListener{

	public void actionPerformed(ActionEvent ae){
		int[] rows = PathwayPanel.pathwayTable.getSelectedRows();
		String[] toDraw = new String[rows.length];
		
		for (int i = 0; i < rows.length; i++) {
			toDraw[i] = (String) ((PathwayTableModel) PathwayPanel.pathwayTable.getModel()).getValueAt(rows[i], 0);
		}
		
		
	}
	
	
}
