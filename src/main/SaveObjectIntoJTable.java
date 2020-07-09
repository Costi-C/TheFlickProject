package main;

import java.io.IOException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableCellRenderer;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import bo.Feed;
import bo.Image;
import test.ObjectToJTable;
import utils.ReadAndWriteUtils;

public class SaveObjectIntoJTable {
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		
		ReadAndWriteUtils rawu = new ReadAndWriteUtils();
		try {
			Feed fd = rawu.readData("input.xml");
			Vector<Image> vector = fd.getFeedData();
			JFrame frame = new JFrame();
			JTable table = new JTable();
			ObjectToJTable model = new ObjectToJTable(vector);
			DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
			centerRenderer.setHorizontalAlignment( JLabel.CENTER );			
			table.setDefaultRenderer(String.class, centerRenderer);
			table.setModel(model);
			//table.getColumnModel().getColumn(0).setPreferredWidth(40);
			frame.add(new JScrollPane(table));
			frame.pack();
			frame.setVisible(true);
			table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
