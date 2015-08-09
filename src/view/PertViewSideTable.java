package view;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class PertViewSideTable extends JPanel{

	private JTable table;
	private JScrollPane scroller;
	
	public PertViewSideTable(Object[][] data)
	{
		String[] tableTitles = {"Node #", 
								"Target Date", 
								"Probability"};
		table = new JTable(data, tableTitles);
		add(table);
		scroller = new JScrollPane(table);
        add(scroller);
	}
	
}
