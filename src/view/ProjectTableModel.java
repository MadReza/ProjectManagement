package view;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import model.Project;

@SuppressWarnings("serial")
public class ProjectTableModel extends AbstractTableModel {

	private ArrayList<Project> allProjects;
	private String[] colNames = {"Name" , "Description" , "Budget" , "Start Date" , "Finish Date" , "Status"};
	
	public ProjectTableModel(){
		allProjects = new ArrayList<Project>();
	}
	
	public void setData(ArrayList<Project> allProjects){
		this.allProjects = allProjects;
	}
	
	public String getColumnName(int column) {
		
		return colNames[column];
	}

	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 6;
	}

	public int getRowCount() {
		
		if(!allProjects.isEmpty()){
			return allProjects.size();
		}
		return 0;
	}

	public Object getValueAt(int row, int col) {
		
		Project project = allProjects.get(row);
		
		switch(col){
		case 0:	
			return project.getName();
		case 1:
			return project.getDescription();
		case 2:
			return project.getBudget();
		case 3:
			return project.getStartDate();
		case 4:
			return project.getFinishDate();
		case 5:
			return project.getStatus();
		}
		return null;
	}

}
