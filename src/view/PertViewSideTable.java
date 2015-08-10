package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class PertViewSideTable extends JPanel{

	private JTable table;
	private JScrollPane scroller;
	
	public PertViewSideTable(String[][] data, int width, int height)
	{
		//instance table model
		DefaultTableModel tableModel = new DefaultTableModel() {

		   @Override
		   public boolean isCellEditable(int row, int column) {
		       //Only the second column
		       return column == 2;
		   }
		};
		String[] tableTitles = {"Node #", 
								"Target Date", 
								"Probability"};
		this.setSize(width/2, height);
		table = new JTable(data, tableTitles);
		System.out.println("table: " + table.getColumnCount());
	//	table.setModel(tableModel);
		table.setSize(width, (height/2) - 40);
		table.setVisible(true);
		add(table);
		scroller = new JScrollPane(table);
        add(scroller);
	}
	
	public JTable getTable()
	{
		return table;
	}
	
}
