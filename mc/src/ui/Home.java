package ui;

import actions.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class Home extends JFrame{

	Container mainPane;
	public DataPanel dp;
	public PathwayPanel pp;
	public CustomDataTable dataTable;
	public CustomDataTable pathwayTable;
	public JPanel drawPanel;
	JTabbedPane tp;
	
	/**
	 * 
	 */
	public Home(){
		setTitle("MEVA Intrapathway Comparison");
		setSize(1600, 1000);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);        
    
		dp = new DataPanel();
		pp = new PathwayPanel();
		dataTable = DataPanel.dataTable;
		
		
		
		
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
		tp.addTab("Data Table", dp);
		tp.addTab("Pathway Table", pp);
		drawPanel = new JPanel();
		drawPanel.setMinimumSize(new Dimension(Integer.MAX_VALUE, 400));
		
		add(drawPanel);
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
