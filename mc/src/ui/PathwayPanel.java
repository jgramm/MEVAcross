package ui;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;

import javax.swing.*;

import java.awt.Dimension;
import java.awt.dnd.*;

import actions.*;
import ui.PathwayTable;
import algorithm.Pathway;


public class PathwayPanel extends JTable {

	public static PathwayTable pathwayTable = new PathwayTable();
	public static List<Pathway> pathways = new ArrayList<Pathway>();
	
	public PathwayPanel(){
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		// new DropTarget(pathwayTable, new DataPanelDNDListener());
		JScrollPane scrollPane = new JScrollPane(pathwayTable);
		pathwayTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
		pathwayTable.setFillsViewportHeight(true);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, buttonPanel.getMinimumSize().height));
		JButton drawNetwork = new JButton("Draw Network");
		JButton drawMetabolome = new JButton("Draw Metabolome");
		
		drawNetwork.addActionListener(new DrawNetworkAction());
		drawMetabolome.addActionListener(new DrawMetabolomeAction());
		
		buttonPanel.add(drawNetwork);
		buttonPanel.add(drawMetabolome);
		add(buttonPanel);
		add(scrollPane);
		
	}

	
	public static void acceptPathways(List<Pathway> analysis){
		pathways = analysis;
		pathwayTable.replaceData(analysis);
		//throw new RuntimeException("DO SOMETHING WITH MEVA ANALYSIS!!!");
	}
	
	
}
