package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import model.Project;

@SuppressWarnings("serial")
public class PertViewFrame extends JFrame{

	private JPanel graphPanel;
	private PertView pertGraph;
	private PertViewSideTable pertTable;
	
	public PertViewFrame(String tabName, Project proj)
	{
		super(tabName);
		pertGraph = new PertView(proj);
		graphPanel = new JPanel();
		pertTable = new PertViewSideTable(new Object[][] {{"1", "10", "0"}});

		graphPanel.add(pertGraph.getGraphComponent());
		getContentPane().add(graphPanel);
		
		//getContentPane().add(pertTable);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setSize(600, 600);
		splitPane.setDividerSize(0);
		splitPane.setDividerLocation(600/2);
		splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setLeftComponent(graphPanel);
		splitPane.setRightComponent(pertTable);

        this.add(splitPane);
	}
}
