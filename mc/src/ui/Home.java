package ui;

import actions.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class Home extends JFrame{

	Container mainPane;
	public DataPanel dp;
	public CustomDataTable dataTable;
	public CustomDataTable pathwayTable;
	JTabbedPane tp;
	
	public Home(){
		setTitle("MEVA Intrapathway Comparison");
		setSize(1600, 1000);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);        
    
		dp = new DataPanel();
		dataTable = DataPanel.dataTable;
		
		dataTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
		dataTable.setFillsViewportHeight(true);
		//Create the scroll pane and add the table to it.
		JScrollPane dataScrollPane = new JScrollPane(dataTable);
		JScrollPane pathwayScrollPane = new JScrollPane(dataTable);
		
		
		JPanel buttonPanel = new JPanel();
		JButton runAnalysis = new JButton("Run Analysis");
		
		runAnalysis.addActionListener(new RunMevaAction());

		/*
        JButton highlightSelectedHMDBs = new JButton("Highlight selected nodes");
		highlightSelectedHMDBs.addActionListener(new HighlightNodesAction());
			
		
		JButton countNeighbors = new JButton("Count neighbors");
		countNeighbors.addActionListener(new CountNeighborsAction());
		
		
		JButton applyData = new JButton("Apply data to networks");
		applyData.addActionListener(new ApplyDataToNodesAction());
		
		
		JButton converter = new JButton("Convert metabolite id's");
		converter.addActionListener(new ConverterAction());

		JButton pwf = new JButton("Add custom pathways");
		pwf.addActionListener(new LoadPWFAction());
        
        */
        
        
        
        
        
        //Add the scroll pane to this panel.
		mainPane = getContentPane();
		tp = new JTabbedPane();
		tp.addTab("Data Table", dataScrollPane);
		tp.addTab("Pathway Table", pathwayScrollPane);
		add(tp);
		
	}
	
	
	public static void main(String[] args) {
	        
		EventQueue.invokeLater(new Runnable() {
			@Override
		    public void run() {
		    	Home ex = new Home();
		    	ex.setVisible(true);
		    }
		});
	    }
	
}
