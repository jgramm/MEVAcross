package panels;

import java.io.*;
import java.util.Arrays;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;


public class ImportFrame extends JFrame{

	public JButton accept;
	public JButton cancel;
	public JTextArea delimSpace;
	public JPanel dataPanel;
	
	public static String[][] sampleData;
	public static String[] data;
	
	public String delimiter = "";
	public JRadioButton space;
	public JRadioButton tab;
	public JRadioButton whitespace;
	public JRadioButton comma;
	public JRadioButton semicolon;
	public static JRadioButton text;
	public ButtonGroup bg;
	
	public enum delimiterType{SPACE, TAB, WHITESPACE, COMMA, SEMICOLON, TEXT}
	public static delimiterType dt = delimiterType.TEXT;
	
	public ImportFrame(){
		JLabel delimReq = new JLabel("Please input data delimiter");
		accept = new JButton("Import data");
		cancel = new JButton("Cancel");
		
		accept.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				buildDataPanel();
			}
		});
		
		cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				MainPanel.dataImportFrame.dispose();
			}
		});
		
		delimSpace.getDocument().addDocumentListener(new DocumentListener(){
			public void insertUpdate(DocumentEvent de){
				ImportFrame.text.doClick();
				ImportFrame.delimitSampleData();
			}

			public void removeUpdate(DocumentEvent de){
				ImportFrame.text.doClick();
				ImportFrame.delimitSampleData();
			}
			
			public void changedUpdate(DocumentEvent de){
				
			}
		});

		space.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				ImportFrame.dt = delimiterType.SPACE;
			}
		});
		
		tab.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				ImportFrame.dt = delimiterType.TAB;
			}
		});
		
		whitespace.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				ImportFrame.dt = delimiterType.WHITESPACE;
			}
		});
		
		comma.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				ImportFrame.dt = delimiterType.COMMA;
			}
		});
		
		semicolon.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				ImportFrame.dt = delimiterType.SEMICOLON;
			}
		});
		
		text.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				ImportFrame.dt = delimiterType.TEXT;
			}
		});
		
		this.add(delimReq);
		this.add(delimSpace);
		this.add(accept);
		this.add(cancel);
	}
	
	public void loadDataFromFileName(String fileName){
		
		try{
			
			String[] startData = new String[20];
			
			BufferedReader reader = new BufferedReader(new FileReader(new File (fileName)));
			if(fileName.endsWith(".txt")){
				
			}
			
			String line = reader.readLine();
			int counter = 0;
			
			while(line != null){
				if(line == ""){
					continue;
				}
				
				startData[counter++] = line;
				
				line = reader.readLine();
			}
			
			data = new String[counter];
			sampleData = new String[counter][];
			
			for(int i = 0; i < counter; i++){
				data[i] = startData[i];
				sampleData[i] = startData[i].split(delimiter);
			}
			
			reader.close();
		} catch(IOException ex){
			ex.printStackTrace();
			System.out.println(ex.getMessage());
			JOptionPane.showMessageDialog(null, "MEVA had a problem parsing your file. Please try again!");
		}
		
	}
	
	public static String getDelimiter(){
		String delimiter = "";
		
		switch (dt){
		case SPACE:
			delimiter = " ";
			break;
		case TAB:
			delimiter = "\t";
			break;
		case WHITESPACE:
			delimiter = "\\s+";
			break;
		case COMMA:
			delimiter = ",";
			break;
		case SEMICOLON:
			delimiter = ";";
			break;
		case TEXT:
			delimiter = text.getText();
			break;
		
		}
		
		return delimiter;
	}
	
	public static void delimitSampleData(){
		String delimiter = getDelimiter();
		for(int i = 0; i < sampleData.length; i++){
			sampleData[i] = data[i].split(delimiter);
		}
	}
	
	
	public void buildDataPanel(){
		
	}
	
}
