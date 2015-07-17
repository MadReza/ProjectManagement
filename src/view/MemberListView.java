package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class MemberListView<T> extends JFrame {

    private static final long serialVersionUID = 1L;
	String title;
	Container container;
	JList<T> assignedList;
	DefaultListModel<T> assignedListModel;
	
	  public MemberListView(String title, ArrayList<T> assignedType) {
	    super(title);
	    
	    // List of Team Members assigned to an activity
	    assignedListModel = new DefaultListModel<T>();
	    for(T elt: assignedType){
	    	assignedListModel.addElement(elt);
	    }
	    
	    assignedList = new JList<T>(assignedListModel);
	    assignedList.setBackground(Color.getHSBColor(0.58f, 0.7f, 0.88f));
	    assignedList.setBorder(BorderFactory.createEtchedBorder());
	    DefaultListCellRenderer renderer = (DefaultListCellRenderer) assignedList.getCellRenderer();
	    renderer.setHorizontalAlignment(JLabel.CENTER);
	    JScrollPane scrollListTeam = new JScrollPane(assignedList);
	    Box assignedListBox = new Box(BoxLayout.Y_AXIS);
	    assignedListBox.setSize(200, 300);
	    assignedListBox.setMinimumSize(new Dimension(200, 100));
	    assignedListBox.add(scrollListTeam);
	    assignedListBox.add(new JLabel("Existing Members"));
	    
	  
	    
	    // Container for the 2 lists and Separator 
	    container = getContentPane();
	    container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
	    
	    container.add(assignedListBox);

	    setSize(200, 300);
	    setMinimumSize(new Dimension(200, 300));
	    setVisible(true);
	  }

	
	public DefaultListModel<T> getAssignedListModel() {
		return assignedListModel;
	}

	public void setAssignedListModel(DefaultListModel<T> assignedListModel) {
		this.assignedListModel = assignedListModel;
	}

	public JList<T> getAssignedList() {
		return assignedList;
	}
			
}
