package ui;

import java.util.*;

import javax.swing.event.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.*;

import ui.DataTableModel;

@SuppressWarnings("serial")
public class CustomDataTable extends JTable implements TableModelListener{

	public Object[][] data;
	public Object[] headers;
	public DataTableModel dtm;
	
	public CustomDataTable(){
		super();
		//setModel(new DataTableModel());
	}
	
	public CustomDataTable(DataTableModel dtm){
		super(dtm);
		this.dtm = dtm;
	}
	
	public CustomDataTable(Object[][] givenData, Object[] givenHeaders){
		//super(new DataTableModel());
		//replaceData((String[][]) givenData, (String[]) givenHeaders);
		super(givenData, givenHeaders);
		//setModel(new DataTableModel());
	}
	
	public void replaceDataAt(int column, String[] newData){
		for(int i = 0; i < data.length; i++){
			data[i][column] = newData[i];
		}
		
		this.getTableHeader().getColumnModel().getColumn(column).setHeaderValue(newData[0]);
	}
	
	// swaps data in table with the given data
	public void replaceData(String[][] givenData, String[] givenHeaders){
		this.setModel(new DataTableModel(givenData, givenHeaders));
		data = givenData;
		headers = givenHeaders;
		
		JTableHeader header = this.getTableHeader();
		TableColumnModel cm = header.getColumnModel();
		
		for(int i = 0; i < headers.length; i++){
			cm.getColumn(i).setHeaderValue(headers[i]);
		}
		
		dtm.fireTableDataChanged();
	}
	
	
	// ONLY USE THIS METHOD IF YOU HAVE TWO COLUMNS OF DATA SELECTED
	public LinkedHashMap<String,Double> getLinkedHashMapData(int[] columnNums){
		LinkedHashMap<String,Double> mapData = new LinkedHashMap<String,Double>();
		
		Arrays.sort(columnNums);
		
		for(int i = 0; i < data.length; i++){
			if(algorithm.NumberUtils.isNumber((String) data[i][columnNums[1]])){
			mapData.put((String) data[i][columnNums[0]], Double.parseDouble((String) data[i][columnNums[1]]));
			}
		}
		
		return mapData;
	}
	
	
	// gets the selected columns of data
	public Object[][] getSelectedColumnValues(int[] columnNums){
		Object[][] selectedColumns = new String[columnNums.length][];
		
		Arrays.sort(columnNums);
		
		for(int i = 0; i < columnNums.length; i++){
			selectedColumns[i] = data[i];
		}
		
		return selectedColumns;
	}
	
	
	

}
