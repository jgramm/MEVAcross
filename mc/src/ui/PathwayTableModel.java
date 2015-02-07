package ui;

import java.util.*;

import javax.swing.table.*;

import algorithm.Pathway;

@SuppressWarnings("serial")
public class PathwayTableModel extends AbstractTableModel{

	public List<Pathway> pathways;
	public String[] columnHeaders = {"Name","Size","Members","P-Value","FDR","NES","Information"};
	
	public PathwayTableModel(){
		super();
	}
	
	public PathwayTableModel(List<Pathway> paths){
		super();
		pathways = paths;
		fireTableDataChanged();
	}
	
	public int getColumnCount(){
		return Pathway.getFieldCount();
	}
	
	public int getRowCount(){
		if(pathways == null){
			return 0;
		} else {
			return pathways.size();
		}
	}
	
	public Object getValueAt(int row, int column){
		return pathways.get(row).getNthValue(column);
	}

	@Override
    public java.lang.Class<?> getColumnClass(int c) {
        return Pathway.getClassN(c);
    }
	
	public void replaceData(List<Pathway> paths){
		pathways = paths;
		fireTableDataChanged();
	}

	
}
