package ui;

import javax.swing.table.*;

@SuppressWarnings("serial")
public class DataTableModel extends AbstractTableModel{

	private String[][] allData;
	private String[] columnHeaders;
	
	public DataTableModel(){
		super();
	}
	
	public DataTableModel(String[][] data, String[] headers){
		super();
		allData = data;
		columnHeaders = headers;
		fireTableDataChanged();
	}
	
	public int getColumnCount(){
		if(columnHeaders == null){
			return 0;
		} else {
			return columnHeaders.length;
		}
	}
	
	public int getRowCount(){
		if(allData == null){
			return 0;
		} else {
			return allData.length;
		}
	}
	
	public Object getValueAt(int row, int column){
		return allData[row][column];
	}

	
	public void replaceData(String[][] data, String[] headers){
		allData = data;
		columnHeaders = headers;
		fireTableDataChanged();
	}





}
