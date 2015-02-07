package ui;

import java.awt.Component;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.event.TableModelListener;
import javax.swing.*;
import javax.swing.table.*;


import algorithm.Pathway;
import ui.PathwayTableModel;
import ui.PathwayTable.DecimalFormatRenderer;
//import org.cytoscape.MEVA.internal.algorithm.PathwayStorage;
//import org.cytoscape.MEVA.internal.parsing.AddToNetworkAction;
//import org.cytoscape.MEVA.internal.parsing.BuildPWFNetwork;
//import org.cytoscape.MEVA.internal.parsing.LoadGPMLAction;

public class PathwayTable extends JTable implements TableModelListener{


	public List<Pathway> pathways;
	public String sample;
	public int columnCount = 7;
	
	public PathwayTable(){
		super();
		this.setModel(new PathwayTableModel());
		this.setAutoCreateRowSorter(true);
		JTableHeader header = this.getTableHeader();
		TableColumnModel cm = header.getColumnModel();
		for(int i = 0; i < columnCount; i++){
			cm.getColumn(i).setHeaderValue(((PathwayTableModel) this.getModel()).columnHeaders[i]);
		}
		
	}
	

	// provides a method for replacing the list of pathways with a new one
	public void replaceData(List<Pathway> pathways){
		this.setModel(new PathwayTableModel(pathways));
		this.pathways = pathways;
		JTableHeader header = this.getTableHeader();
		TableColumnModel cm = header.getColumnModel();
		for(int i = 3; i <= 5; i++){
			// draws p-value, fdr, and nes as decimals
			getColumnModel().getColumn(i).setCellRenderer(new DecimalFormatRenderer());
		}

		for(int i = 0; i < columnCount; i++){
			cm.getColumn(i).setHeaderValue(((PathwayTableModel) this.getModel()).columnHeaders[i]);
		}
		
	}

	
	
	public class DecimalFormatRenderer extends DefaultTableCellRenderer{
		// method for formating numbers as decimals
		private final DecimalFormat formatter = new DecimalFormat( "#.000000000000" );
		public Component getTableCellRendererComponent (JTable table, 
				Object obj, boolean isSelected, boolean hasFocus, int row, int column) {
			obj = formatter.format((Number) obj);
			return super.getTableCellRendererComponent(table, obj, isSelected, hasFocus, row, column);
		}
	}

	
	
	
/*	// loads a gpml or pwf file when told to
	public void processSelectedPathways(String action){
		int rowCount = getSelectedRowCount();
		int myRow = convertRowIndexToModel(getSelectedRow());
		
		// load one file
		if(rowCount == 1){
			String path = pathways.get(myRow).getName();
			
			// if it's a pwf file
			if(PathwayStorage.customPathwayFiles.containsKey(path)){//gpmlNames.contains(path)){////////////////////////////////////////////// DEAL WITH THIS JAMES THIS IS IMPORTANT!!!!! ///////////////////////////////////////
				////////////////////////////////////// THIS WILL CAUSE AN ERROR IF YOU TRY TO ADD A PWF FILE TO THE CURRENT NETWORK
				// if you are loading a new network
				if(action == "load"){
					try{
						// load the new pwf file
						BuildPWFNetwork.readPWF(PathwayStorage.customPathwayFiles.get(path));
					} catch(IOException ex){
						ex.printStackTrace();
						throw new RuntimeException(ex.getMessage());
					}
				}
				// otherwise you're loading a gpml file
			} else { 
				// either load the file
				if(action == "load"){
					LoadGPMLAction.loadSingleGPMLFile(path);
				} else {
				// or add it to the current network
					AddToNetworkAction.loadSingleGPMLFile(path);
				}
			}
		}
		
		// same as above but for multiple files  //// THIS WILL ACTUALLY CAUSE A BUG IF SOME ARE CUSTOM PWFS AND SOME ARE GPML 
		else if(rowCount > 1){
			int[] rows = getSelectedRows();
			
			List<String> paths = new ArrayList<String>();
			
			for(int i = 0; i < rows.length; i++){
				paths.add(pathways.get(convertRowIndexToModel(rows[i])).getName());
			}
			
			if(action == "load"){
				LoadGPMLAction.loadMultipleGPMLFiles(paths);
			} else {
				AddToNetworkAction.loadMultipleGPMLFiles(paths);
			}
		}
		
		else if(rowCount < 1){
			JOptionPane.showMessageDialog(new JFrame(), "Please select at least one network to load");
		}
		
	}
	*/
}
