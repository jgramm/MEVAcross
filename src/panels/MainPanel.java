package panels;

import org.broadinstitute.MEVA.algorithm.MEVAProps;

import java.io.*;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.dnd.DropTarget;

public class MainPanel {

	public static enum PanelMode{DnD}
	
	public static JFrame frame;// = new JFrame("MEVA");
	public static JPanel DnDPanel = new JPanel();
	public static ImportFrame dataImportFrame = new ImportFrame();
	
	public static void main(String[] args){
		init();
		setDnDMode();
	}
	
	public static void init(){
		frame = new JFrame("MEVA");
		frame.setSize(800, 600);
		frame.setVisible(true);
	}
	
	
	public static void setDnDMode(){
		init();
		JLabel myLabel = new JLabel("Drag something here!", SwingConstants.CENTER);

	    // Create the drag and drop listener
	    DnDDataListener myDragDropListener = new DnDDataListener();

	    // Connect the label with a drag and drop listener
	    new DropTarget(myLabel, myDragDropListener);

	    // Add the label to the content
	    frame.getContentPane().add(BorderLayout.CENTER, myLabel);

	    // Show the frame
	    frame.setVisible(true);

	}
	
	
	
	
	
	public static MEVAProps getProps(){
		return new MEVAProps();
	}
	
}
