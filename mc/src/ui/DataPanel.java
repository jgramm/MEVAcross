package ui;

import java.io.*;
import java.util.LinkedHashMap;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.dnd.*;
import actions.RunMevaAction;

import ui.CustomDataTable;

public class DataPanel extends JPanel{

	private static final long serialVersionUID = -338371323663965906L;
	public static CustomDataTable dataTable = new CustomDataTable(new DataTableModel());
	
	public DataPanel(){
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		new DropTarget(this, new DataPanelDNDListener());
		
		load("C:/Users/James/workspace/MEVA/SIRT3_MEFs_FC.txt");
		
		JScrollPane scrollPane = new JScrollPane(dataTable);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, buttonPanel.getMinimumSize().height));
		
		JButton runAnalysis = new JButton("Run Analysis");
		runAnalysis.addActionListener(new RunMevaAction());

		
		buttonPanel.add(runAnalysis);
		
		add(buttonPanel);
		add(scrollPane);
		
		dataTable.setColumnSelectionAllowed(true);
		
		
	}
	
	public static void load(String fileName){
		System.out.println("a");
		String delimiter = "\t";
		try{
			BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));
			int counter = 0;
			String line = reader.readLine();
			line = reader.readLine();
			
			while(line != null){
				if(line.isEmpty()){
					line = reader.readLine();
					continue;
				}
				counter++;
				line = reader.readLine();
			}
			
			String[][] data = new String[counter][];
			reader = new BufferedReader(new FileReader(new File(fileName)));
			line = reader.readLine();
			String[] headers = line.split(delimiter);
			line = reader.readLine();
			System.out.println(counter);
			counter = 0;
			while(line != null){
				if(line.isEmpty()){
					line = reader.readLine();
					continue;
				}
				
				data[counter] = line.split(delimiter);
				
				counter++;
				line = reader.readLine();
			}
			
			reader.close();
			System.out.println("j");
			dataTable.replaceData(data, headers);
			System.out.println("k");
			
			
		} catch(IOException ex){
			ex.printStackTrace();
			System.out.println(ex.getMessage());
		}
	}

	// returns a linked hashmap of the first two selected columns
	public static LinkedHashMap<String,Double> getLinkedHashMapData(){
		int[] columnNums = dataTable.getSelectedColumns();
		 LinkedHashMap<String,Double> LHMD = dataTable.getLinkedHashMapData(columnNums);
		 return LHMD;
	}
	
	// returns an array containing the information in the leftmost selected column
	public static Object[] getSelectedColumn(){
		int c = dataTable.getSelectedColumn();
		//int[] cs = {c};
		Object[][] data = dataTable.data;
		String[] column = new String[data.length];
		
		for(int i = 0; i < data.length; i++){
			column[i] = (String) data[i][c];
		}
		
		return column;
	}
	
	
	// returns an array of arrays containing all selected columns
	public static Object[][] getSelectedData(){
		int[] columnNums = dataTable.getSelectedColumns();
		Object[][] data = dataTable.getSelectedColumnValues(columnNums);
		return data;
	}

	
	
}
