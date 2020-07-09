package test;

import java.sql.Date;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import bo.Author;
import bo.Image;


public class ObjectToJTable extends AbstractTableModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Vector<Image> imgVector = new Vector<Image>();
	private String[] columnNames = {"Nr.", "ImageTitle", " ImageURL", "Author", "AuthorURL", "AuthorIcon", "ImageDate"};
	private Class<?>[] columnTypes = {Integer.class, String.class, String.class, String.class, String.class, String.class, Date.class};
	
	public ObjectToJTable(Vector<Image> imgVector){
		this.imgVector = imgVector;
	}
	
	@Override	
	public int getRowCount() {
		return imgVector.size();
	}
	
	@Override
	public String getColumnName(int columnIndex) {
		return columnNames[columnIndex];
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return rowIndex;
		case 1:
			return imgVector.get(rowIndex).getTitle();
		case 2:
			return imgVector.get(rowIndex).getUrl();
		case 3:
			return imgVector.get(rowIndex).getAuthor().getName();
		case 4:
			return imgVector.get(rowIndex).getAuthor().getUrl();
		case 5:
			return imgVector.get(rowIndex).getAuthor().getIcon();
		case 6:
			return imgVector.get(rowIndex).getImageDate();		
		default:
			return "";			
		}
		
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return columnTypes[columnIndex];
		
//		switch (columnIndex) {
//		case 0:
//			return String.class;
//		case 1:
//			return String.class;
//		case 2:
//			return String.class;
//		case 3:
//			return String.class;
//		case 4:
//			return String.class;
//		case 5:
//			return Date.class;		
//		}
//		return null;
	}

	
}
