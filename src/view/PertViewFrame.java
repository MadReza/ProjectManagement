package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.PertNode;
import model.Project;

@SuppressWarnings("serial")
public class PertViewFrame extends JFrame{

	private JPanel graphPanel;
	private PertView pertGraph;
	private PertViewSideTable pertTablePanel;
	private JScrollPane graphScroller;
	private JButton calculateButton;
	private String[][] tableData;
	
	public PertViewFrame(String tabName, Project proj, int width, int height)
	{
		super(tabName);
		
		pertGraph = new PertView(proj);
		graphPanel = new JPanel();
		graphPanel.setSize(width, height/2);
		tableData = new String[pertGraph.getNodes().size()-1][3];
		fillTableData();
		
		pertTablePanel = new PertViewSideTable(tableData, width, height);
		
		calculateButton = new JButton("Calculate");
        calculateButton.setPreferredSize(new Dimension(120, 40));
        calculateButton.setForeground(Color.BLUE);
        calculateButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        pertTablePanel.add(calculateButton);
        
		Border noBorder = BorderFactory.createEmptyBorder();
		pertGraph.getGraphComponent().setBorder(noBorder);
		
		graphPanel.add(pertGraph.getGraphComponent());
		graphScroller = new JScrollPane();
		graphScroller.setVisible(true);
		graphScroller.setAutoscrolls(true);
		graphPanel.add(graphScroller);
		
		getContentPane().add(graphPanel);
        getContentPane().add(pertTablePanel);
        
		JSplitPane splitPane = new JSplitPane();
		splitPane.setSize(width, height);
		splitPane.setDividerSize(0);
		splitPane.setDividerLocation(height/2);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setLeftComponent(graphPanel);
		splitPane.setRightComponent(pertTablePanel);

        this.add(splitPane);
        
        calculateButton.addActionListener(new calculateProbability());
	}
	
	private void fillTableData()
	{
		for(int i=1; i<pertGraph.getNodes().size(); i++)
		{
			tableData[i-1][0] = new String(String.valueOf(pertGraph.getNodes().get(i).getID()));
			tableData[i-1][1] = new String("-");
			tableData[i-1][2] = new String("-");
		}
	}
	
	private class calculateProbability implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			float zValue = 0.0f;
			ArrayList<PertNode> nodes = pertGraph.getNodes();
			for(int i=0; i<pertTablePanel.getTable().getRowCount(); i++)
			{
				if(isNumeric((String) pertTablePanel.getTable().getValueAt(i, 1)))
				{
					zValue = Integer.valueOf((String) pertTablePanel.getTable().getValueAt(i, 1)) - nodes.get(Integer.valueOf((String) tableData[i][0])-1).getExpectedDate();
					zValue = zValue / pertGraph.getNodes().get(Integer.valueOf((String) tableData[i][0])-1).getStandardDeviation();
					System.out.println(zValue/*pertTablePanel.getTable().getValueAt(i, 0)*/);
					pertTablePanel.getTable().setValueAt(String.valueOf(zValue), i, 2);
				}
			}
			
		}
	}
	private boolean isNumeric(String str)
	{
	  return str.matches("\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
	}
	
}
